package com.task.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.task.library.dto.ListDTO;
import com.task.library.dto.TaskDTO;
import com.task.library.exception.AlreadyExistsException;
import com.task.library.exception.TaskNotFoundException;
import com.task.library.service.ListService;
import com.task.library.service.TaskListService;
import com.task.library.service.TaskService;
import com.task.model.Task;
import com.task.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ListService listService;

	@Autowired
	private TaskListService taskListService;
	
	@Override
	public Optional<TaskDTO> findTaskById(String userId, Long taskId) {
		
		Task task = taskRepository.findByTaskIdAndUserId(taskId, userId).orElse(null);
		
		if(task == null) {
			return Optional.empty();
		}
		return Optional.of(toTaskDTO(task));
	}

	@Override
	public Optional<List<TaskDTO>> listTaskOfUser(String userId) {
		List<Task> tasks = taskRepository.findByUserId(userId).orElse(null);
		
		if(tasks == null) {
			return Optional.empty();
		}
		List<TaskDTO> taskDTOs = tasks
									.stream()
									.map(this::toTaskDTO)
									.toList();
		return Optional.of(taskDTOs);
	}

	@Override
	public Long createTask(TaskDTO taskDTO) throws Exception {
		
		if(taskRepository.findByTaskNameAndUserId(taskDTO.getTaskName(),
				taskDTO.getUserId()).isPresent()) {
			throw new AlreadyExistsException("Task with this name already exists", 400);
		}

		sanitizeInputs(taskDTO);
		Task taskPayload = Task.toTask(taskDTO);
		
		if(taskPayload.getDueDate() == null) {
			taskPayload.setDueDate(LocalDate.now());
		}
		
		Task createdTask = taskRepository.save(taskPayload);
		
		if(createdTask != null) {
			return createdTask.getTaskId();
		}
		throw new Exception("Error while creating task");
	}

	@Override
	public TaskDTO updateTask(TaskDTO taskDTO, boolean removeList)
			throws TaskNotFoundException, AlreadyExistsException {
		Optional<Task> existingTask = taskRepository
									  .findByTaskIdAndUserId(taskDTO.getTaskId(),
											  				 taskDTO.getUserId());
		if(existingTask.isEmpty()) {
			throw new TaskNotFoundException("Not task found with the given taskId");
		}
		sanitizeInputs(taskDTO);
		Task newTask = Task.toTask(taskDTO);
		checkUpdateTaskDetails(existingTask.get(), newTask);

		if(!existingTask.get().getTaskName().equals(newTask.getTaskName())) {
			checkSameTaskName(newTask);
		}
		Task task = taskRepository.save(newTask);
		LOGGER.info("Updated task => " + task.getTaskId());

		List<ListDTO> lists = taskDTO.getLists();
		TaskDTO updatedTask = toTaskDTO(task);
		
		if(removeList || !lists.isEmpty()) {
			List<ListDTO> updatedLists = taskListService.addListsToTask(updatedTask, lists, removeList);
		
			updatedTask.setLists(updatedLists);
			LOGGER.info("Saved Lists related to task => " + updatedTask.getTaskId());
		}
		
		return updatedTask;
	}

	@Override
	public Long deleteTask(String userId, Long taskId) {
		taskListService.deleteAllRelationOfTask(taskId);
		List<Task> task = taskRepository.deleteByUserIdAndTaskId(userId, taskId);
		
		if(task != null && task.size() > 0) {
			return task.get(0).getTaskId();
		}
		return null;
	}

	@Override
	public Optional<List<TaskDTO>> getAllSubTasks(String userId, Long parentTaskId) {
		Optional<List<Task>> subTasks = taskRepository
									.findByUserIdAndParentTask(userId, parentTaskId);
		
		if(subTasks.isEmpty()) {
			return null;
		}
		List<TaskDTO> taskDTOs = subTasks.get()
											.stream()
											.map(this::toTaskDTO)
											.toList();
		taskDTOs.forEach(task -> {
			Optional<List<TaskDTO>> sTasks = getAllSubTasks(userId, task.getTaskId());
			task.setSubTasks(sTasks.orElse(null));
		});
		return Optional.of(taskDTOs);
	}
	
	@Override
	public boolean isTaskExists(String userId, Long taskId) {
		if(taskId == null || userId == null) {
			throw new IllegalArgumentException("Invalid Input");
		}
		return taskRepository.existsByUserIdAndTaskId(userId, taskId);
	}

	@Override
	public boolean toggleTask(String userId, Long taskId) throws TaskNotFoundException {
		if(taskId == null || userId == null) {
			throw new IllegalArgumentException("Invalid Input");
		}
		Optional<Task> existingTask = taskRepository
				.findByTaskIdAndUserId(taskId, userId);
		if(existingTask.isEmpty()) {
			throw new TaskNotFoundException("Not task found with the given taskId");
		}

		existingTask.get().setCompleted(!existingTask.get().isCompleted());
		Task task = taskRepository.save(existingTask.get());

		return task.isCompleted();
	}

	@Override
	public Optional<List<TaskDTO>> findByDate(String userId, LocalDate date) {
		if(date == null || userId == null) {
			throw new IllegalArgumentException("Invalid Input");
		}
		Optional<List<Task>> tasks =  taskRepository.findByUserIdAndDueDate(userId, date);
		if(tasks.isEmpty()) {
			return Optional.empty();
		}
		List<TaskDTO> taskDTOS = tasks.get().stream().map(this::toTaskDTO).toList();

		return Optional.of(taskDTOS);
	}

	@Override
	public Optional<List<TaskDTO>> getUpcomingTasks(String userId) {
		if(userId == null) {
			throw new IllegalArgumentException("User id is empty");
		}
		Optional<List<Task>> tasks =
				taskRepository.findByUserIdAndDueDateGreaterThan(userId, LocalDate.now());
		if(tasks.isEmpty()) {
			return Optional.empty();
		}
		List<TaskDTO> taskDTOS = tasks.get().stream().map(this::toTaskDTO).toList();

		return Optional.of(taskDTOS);
	}

	@Override
	public Optional<List<TaskDTO>> getThisWeekTasks(String userId) {
		LocalDate today = LocalDate.now();
		LocalDate nextSaturday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

		Optional<List<Task>> tasks =
				taskRepository.findByUserIdAndDueDateGreaterThanEqualAndDueDateLessThanEqual(userId,
								today, nextSaturday);
		if(tasks.isEmpty()) {
			return Optional.empty();
		}
		List<TaskDTO> taskDTOS = tasks.get().stream().map(this::toTaskDTO).toList();
		return Optional.of(taskDTOS);
	}

	private TaskDTO toTaskDTO(Task task) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setUserId(task.getUserId());
		taskDTO.setDescription(task.getDescription());
		taskDTO.setTaskId(task.getTaskId());
		taskDTO.setTaskName(task.getTaskName());
		taskDTO.setDueDate(task.getDueDate());
		taskDTO.setParentTaskId(task.getParentTask());
		taskDTO.setIsCompleted(task.isCompleted());
		
		Optional<List<TaskDTO>> subTasks = getAllSubTasks(task.getUserId(), task.getTaskId());
		taskDTO.setSubTasks(subTasks.isPresent() ? subTasks.get() : null);
		
		Optional<List<ListDTO>> lists = listService.getListsOfTask(task.getUserId(),
																	task.getTaskId());
		taskDTO.setLists(lists.isPresent() ? lists.get() : null);
		
		return taskDTO;
	}
	
	private void checkUpdateTaskDetails(Task existingTask, Task newTask) {
		
		if(newTask.getDescription() == null && existingTask.getDescription() != null) {
			newTask.setDescription(existingTask.getDescription());
		}
		if(newTask.getTaskName() == null && existingTask.getTaskName() != null) {
			newTask.setTaskName(existingTask.getTaskName());
		}
		if(newTask.getDueDate() == null && existingTask.getDueDate() != null) {
			newTask.setDueDate(existingTask.getDueDate());
		}
		if(newTask.getParentTask() == null && existingTask.getParentTask() != null) {
			newTask.setParentTask(existingTask.getParentTask());
		}
	}

	private void checkSameTaskName(Task task) throws AlreadyExistsException {
		Optional<Task> resultTask = taskRepository
				.findByTaskNameAndUserId(task.getTaskName(), task.getUserId());

		if(resultTask.isEmpty()) {
			return;
		}
		if(resultTask.get().getTaskId() != task.getTaskId()) {
			throw new AlreadyExistsException("Task Name already exists", 400);
		}
	}
	
	private void sanitizeInputs(TaskDTO taskDTO) {
		if(taskDTO.getTaskName() != null) {
			
			taskDTO.setTaskName(taskDTO.getTaskName().trim());
			
			if(taskDTO.getTaskName().length() < 3) {
				throw new IllegalArgumentException("Task name length should be greater than or equal to 3");
			}
			if(taskDTO.getTaskName().length() > 50) {
				throw new IllegalArgumentException("Task name length should be lesser than or equal to 50");
			}

			taskDTO.setTaskName(HtmlUtils.htmlEscape(taskDTO.getTaskName()));
		}
		
		if(taskDTO.getDescription() != null) {
			taskDTO.setDescription(taskDTO.getDescription().trim());
			taskDTO.setDescription(HtmlUtils.htmlEscape(taskDTO.getDescription()));
		}
	}
}
