package com.task.resource.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.task.library.dto.list.ListDTO;
import com.task.library.dto.task.TaskDTO;
import com.task.library.dto.task.TaskListDTO;
import com.task.library.exception.AlreadyExistsException;
import com.task.library.exception.AppException;
import com.task.library.exception.TaskNotFoundException;
import com.task.library.kafka.KafkaAppEvent;
import com.task.library.kafka.KafkaTaskMessage;
import com.task.library.kafka.KafkaTopics;
import com.task.library.service.ListService;
import com.task.library.service.TaskListService;
import com.task.library.service.TaskService;
import com.task.resource.annotation.TimeLogger;
import com.task.resource.model.Task;
import com.task.resource.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ListService listService;

    @Autowired
    private TaskListService taskListService;

    @Override
    public Optional<TaskDTO> findTaskById(String userId, String taskId) {

        Task task = taskRepository.findByTaskIdAndUserId(taskId, userId).orElse(null);

        if (task == null) {
            return Optional.empty();
        }
        TaskDTO taskDTO = toTaskDTO(task);
        decodeData(taskDTO);
        return Optional.of(taskDTO);
    }

    @TimeLogger
    @Override
    public Optional<List<TaskDTO>> listTaskOfUser(String userId) throws AppException {
        List<Task> tasks = taskRepository.findByUserId(userId).orElse(null);

        if (tasks == null) {
            return Optional.empty();
        }
        List<TaskDTO> taskDTOs = tasks.stream().map(this::toTaskDTO).collect(Collectors.toList());
        taskDTOs.forEach(this::decodeData);
        return Optional.of(taskDTOs);
    }

    @Override
    @TimeLogger
    public String createTask(TaskDTO taskDTO) throws Exception {

        if (taskRepository.findByTaskNameAndUserId(taskDTO.getTaskName(), taskDTO.getUserId()).isPresent()) {
            throw new AlreadyExistsException("Task with this name already exists", 400);
        }

        sanitizeInputs(taskDTO);
        Task taskPayload = Task.toTask(taskDTO);

        if (taskPayload.getDueDate() == null) {
            taskPayload.setDueDate(LocalDate.now());
        }
        taskPayload.setCreatedTime(LocalDateTime.now());

        Task createdTask = taskRepository.save(taskPayload);

        if (taskDTO.getLists() != null && !taskDTO.getLists().isEmpty()) {
            taskListService.addListsToTask(createdTask.getUserId(), createdTask.toTaskDTO(), taskDTO.getLists(), false);

            LOGGER.info("Saved Lists related to task => " + createdTask.getTaskId());
        }

        if (createdTask != null) {

            LOGGER.info("Created task, TaskId => {}", createdTask.getTaskId());
            return createdTask.getTaskId();
        }
        LOGGER.error("Unable to create task!");
        throw new Exception("Error while creating task");
    }

    @Override
    @TimeLogger
    public TaskDTO updateTask(TaskDTO taskDTO, boolean removeList) throws AppException {
        Optional<Task> existingTask = taskRepository.findByTaskIdAndUserId(taskDTO.getTaskId(), taskDTO.getUserId());
        if (existingTask.isEmpty()) {
            LOGGER.error("Task Not found with the give taskId {}", taskDTO.getTaskId());
            throw new TaskNotFoundException("No task found with the given taskId");
        }
        sanitizeInputs(taskDTO);
        Task newTask = Task.toTask(taskDTO);

        checkUpdateTaskDetails(existingTask.get(), newTask, taskDTO.getIsCompleted() != null);
        if (!existingTask.get().getTaskName().equals(newTask.getTaskName())) {
            checkSameTaskName(newTask);
        }

        Task task = taskRepository.save(newTask);
        LOGGER.info("Updated task => {}", task.getTaskId());

        List<ListDTO> lists = taskDTO.getLists();
        TaskDTO updatedTask = toTaskDTO(task);

        if (removeList || !lists.isEmpty()) {
            List<ListDTO> updatedLists =
                    taskListService.addListsToTask(taskDTO.getUserId(), updatedTask, lists, removeList);

            updatedTask.setLists(updatedLists);
            LOGGER.info("Saved Lists related to task => {}", updatedTask.getTaskId());
        }
        decodeData(updatedTask);
        return updatedTask;
    }

    @Override
    @TimeLogger
    public String deleteTask(String userId, String taskId) {
        taskListService.deleteAllRelationOfTask(userId, taskId);
        List<Task> task = taskRepository.deleteByUserIdAndTaskId(userId, taskId);

        if (task != null && task.size() > 0) {

            LOGGER.info("Deleted task => {}", task.get(0).getTaskId());
            return task.get(0).getTaskId();
        }
        return null;
    }

    @Override
    @TimeLogger
    public boolean isTaskExists(String userId, String taskId) {
        if (taskId == null || userId == null) {
            throw new IllegalArgumentException("Invalid Input");
        }
        return taskRepository.existsByUserIdAndTaskId(userId, taskId);
    }

    @Override
    @TimeLogger
    public boolean toggleTask(String userId, String taskId) throws TaskNotFoundException {
        if (taskId == null || userId == null) {
            throw new IllegalArgumentException("Invalid Input");
        }
        Optional<Task> existingTask = taskRepository.findByTaskIdAndUserId(taskId, userId);
        if (existingTask.isEmpty()) {
            throw new TaskNotFoundException("No task found with the given taskId");
        }

        existingTask.get().setIsCompleted(!existingTask.get().getIsCompleted());
        Task task = taskRepository.save(existingTask.get());

        LOGGER.info("Toggled task status to {}", task.getIsCompleted() ? "Completed" : "Yet2Finish");
        return task.getIsCompleted();
    }

    @Override
    @TimeLogger
    public Optional<List<TaskDTO>> findByDate(String userId, LocalDate date) {
        if (date == null || userId == null) {
            throw new IllegalArgumentException("Invalid Input");
        }
        Optional<List<Task>> tasks = taskRepository.findByUserIdAndDueDate(userId, date);
        if (tasks.isEmpty()) {
            return Optional.empty();
        }
        List<TaskDTO> taskDTOS = tasks.get().stream().map(this::toTaskDTO).collect(Collectors.toList());
        taskDTOS.forEach(this::decodeData);
        return Optional.of(taskDTOS);
    }

    @Override
    @TimeLogger
    public Optional<List<TaskDTO>> getUpcomingTasks(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id is empty");
        }
        Optional<List<Task>> tasks = taskRepository.findByUserIdAndDueDateGreaterThanEqual(userId, LocalDate.now());
        if (tasks.isEmpty()) {
            return Optional.empty();
        }
        List<TaskDTO> taskDTOS = tasks.get().stream().map(this::toTaskDTO).collect(Collectors.toList());
        taskDTOS.forEach(this::decodeData);
        return Optional.of(taskDTOS);
    }

    @Override
    @TimeLogger
    public Optional<List<TaskDTO>> getThisWeekTasks(String userId) {
        LocalDate today = LocalDate.now();
        LocalDate nextSaturday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        Optional<List<Task>> tasks = taskRepository.findByUserIdAndDueDateBetween(userId, today, nextSaturday);
        if (tasks.isEmpty()) {
            return Optional.empty();
        }
        List<TaskDTO> taskDTOS = tasks.get().stream().map(this::toTaskDTO).collect(Collectors.toList());
        taskDTOS.forEach(this::decodeData);
        return Optional.of(taskDTOS);
    }

    @Override
    @TimeLogger
    public Optional<List<TaskDTO>> getTasksOfList(String userId, String listId) throws AppException {

        Optional<ListDTO> list = listService.findByListId(userId, listId);
        if (list.isEmpty()) {
            throw new AppException("List not found", HttpStatus.BAD_REQUEST.value());
        }
        Optional<List<TaskListDTO>> taskLists = taskListService.findByList(userId, list.get());
        if (taskLists.isPresent()) {
            List<TaskDTO> tasks = taskLists.get().stream().map(taskList -> {
                return findTaskById(userId, taskList.getTaskDTO().getTaskId()).orElse(null);
            }).collect(Collectors.toList());
            tasks.forEach(this::decodeData);
            return Optional.of(tasks);
        }
        return Optional.empty();
    }

    @Override
    @TimeLogger
    public Optional<List<TaskDTO>> getTasksLessThanDate(String userId, LocalDate date) {
        Optional<List<Task>> tasks = taskRepository.findByUserIdAndDueDateLessThan(userId, date);
        if (tasks.isEmpty()) {
            return Optional.empty();
        }
        List<TaskDTO> taskDTOs = tasks.get().stream().map(this::toTaskDTO).collect(Collectors.toList());
        taskDTOs.forEach(this::decodeData);
        return Optional.of(taskDTOs);
    }

    private TaskDTO toTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setUserId(task.getUserId());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTaskName(task.getTaskName());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setIsCompleted(task.getIsCompleted());
        taskDTO.setCreatedTime(task.getCreatedTime());

        try {
            Optional<List<ListDTO>> lists = listService.getListsOfTask(task.getUserId(), task.getTaskId(), true);
            taskDTO.setLists(lists.isPresent() ? lists.get() : null);
        } catch (AppException e) {
            e.printStackTrace();
        }

        return taskDTO;
    }

    private void checkUpdateTaskDetails(Task existingTask, Task newTask, boolean isNewCompletedValue) {

        if (newTask.getDescription() == null && existingTask.getDescription() != null) {
            newTask.setDescription(existingTask.getDescription());
        }
        if (newTask.getTaskName() == null && existingTask.getTaskName() != null) {
            newTask.setTaskName(existingTask.getTaskName());
        }
        if (newTask.getDueDate() == null && existingTask.getDueDate() != null) {
            newTask.setDueDate(existingTask.getDueDate());
        }
        if (!isNewCompletedValue) {
            newTask.setIsCompleted(existingTask.getIsCompleted());
        }
    }

    private void checkSameTaskName(Task task) throws AlreadyExistsException {
        Optional<Task> resultTask = taskRepository.findByTaskNameAndUserId(task.getTaskName(), task.getUserId());

        if (resultTask.isEmpty()) {
            return;
        }
        if (resultTask.get().getTaskId() != task.getTaskId()) {
            throw new AlreadyExistsException("Task Name already exists", 400);
        }
    }

    private void sanitizeInputs(TaskDTO taskDTO) {
        if (taskDTO.getTaskName() != null) {

            taskDTO.setTaskName(taskDTO.getTaskName().trim());

            if (taskDTO.getTaskName().length() < 3) {
                throw new IllegalArgumentException("Task name length should be greater than or equal to 3");
            }
            if (taskDTO.getTaskName().length() > 50) {
                throw new IllegalArgumentException("Task name length should be lesser than or equal to 50");
            }

            taskDTO.setTaskName(HtmlUtils.htmlEscape(taskDTO.getTaskName()));
        }

        if (taskDTO.getDescription() != null) {

            if (taskDTO.getDescription().trim().length() > 200) {
                throw new IllegalArgumentException("Task description length should be lesser than or equal to 200");
            }
            taskDTO.setDescription(taskDTO.getDescription().trim());
            taskDTO.setDescription(HtmlUtils.htmlEscape(taskDTO.getDescription()));
        }
    }

    private void decodeData(TaskDTO taskDTO) {
        if (taskDTO.getTaskName() != null) {
            taskDTO.setTaskName(HtmlUtils.htmlUnescape(taskDTO.getTaskName()));
        }
        if (taskDTO.getDescription() != null) {
            taskDTO.setDescription(HtmlUtils.htmlUnescape(taskDTO.getDescription()));
        }
    }

}
