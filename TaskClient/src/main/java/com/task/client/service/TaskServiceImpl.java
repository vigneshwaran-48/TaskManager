package com.task.client.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.task.library.dto.task.TaskBodyResponse;
import com.task.library.dto.task.TaskCreationPayload;
import com.task.library.dto.task.TaskCreationResponse;
import com.task.library.dto.task.TaskDTO;
import com.task.library.dto.task.TaskDeletionResponse;
import com.task.library.dto.task.TaskListBodyResponse;
import com.task.library.dto.task.TaskToggleResponse;
import com.task.library.exception.AppException;
import com.task.library.service.TaskService;

import reactor.core.publisher.Mono;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private WebClient webClient;
  
    @Value("${app.resource.server.baseurl}")
    private String resourceServerBaseURL;
    
    private final static String BASE_URL = "/api/v1/task";    
    private final static String SLASH = "/";


    @Override
    public Optional<TaskDTO> findTaskById(String userId, String taskId) throws AppException {
        Mono<TaskBodyResponse> response = webClient
                                    .get()
                                    .uri(BASE_URL + SLASH + taskId)
                                    .retrieve()
                                    .bodyToMono(TaskBodyResponse.class);
        
        TaskBodyResponse taskBodyResponse = response.block();

        if(taskBodyResponse.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        if(taskBodyResponse.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(taskBodyResponse.getMessage(), taskBodyResponse.getStatus());
        }

        return Optional.of(taskBodyResponse.getTask());
    }

    @Override
    public Optional<List<TaskDTO>> listTaskOfUser(String userId) throws AppException {
        
        Mono<TaskListBodyResponse> response = webClient.get()
                                                 .uri(BASE_URL)
                                                 .retrieve()
                                                 .bodyToMono(TaskListBodyResponse.class);
        
        TaskListBodyResponse taskListBodyResponse = response.block();

        if(taskListBodyResponse.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        if(taskListBodyResponse.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(taskListBodyResponse.getMessage(), taskListBodyResponse.getStatus());
        }
        return Optional.of(taskListBodyResponse.getTasks());
    }

    @Override
    public String createTask(TaskDTO taskDTO) throws Exception {

        TaskCreationPayload payload = new TaskCreationPayload();
        payload.setTaskName(taskDTO.getTaskName());
        payload.setDescription(taskDTO.getDescription());
        payload.setDueDate(taskDTO.getDueDate());
        payload.setIsCompleted(taskDTO.getIsCompleted());
        
        if(taskDTO.getLists() != null && !taskDTO.getLists().isEmpty()) {
            payload.setLists(taskDTO.getLists()
                                    .stream()
                                    .map(list -> list.getListId())
                                    .toList());
        }
        payload.setUserId(taskDTO.getUserId());
        Mono<TaskCreationResponse> response = webClient.post()
                                                        .uri(BASE_URL)
                                                        .body(Mono.just(payload), TaskCreationPayload.class)
                                                        .retrieve()
                                                        .bodyToMono(TaskCreationResponse.class);
        TaskCreationResponse taskCreationResponse = response.block();
        if(taskCreationResponse.getStatus() != HttpStatus.CREATED.value()) {
            throw new AppException(taskCreationResponse.getMessage(), taskCreationResponse.getStatus());
        }       
        return taskCreationResponse.getTaskId();                                       
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO, boolean removeList)
            throws AppException {

        StringBuffer urlBuffer = new StringBuffer(BASE_URL);
        urlBuffer.append(SLASH).append(taskDTO.getTaskId()).append("?")
                 .append("removeListNotIncluded").append("=").append(removeList);

        Mono<TaskBodyResponse> response = webClient.patch()
                                                    .uri(urlBuffer.toString())
                                                    .body(Mono.just(taskDTO), TaskDTO.class)
                                                    .retrieve()
                                                    .bodyToMono(TaskBodyResponse.class);
        TaskBodyResponse taskBodyResponse = response.block();
        if(taskBodyResponse.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return null;
        }
        else if(taskBodyResponse.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(taskBodyResponse.getMessage(), taskBodyResponse.getStatus());
        }
        return taskBodyResponse.getTask();
    }

    @Override
    public String deleteTask(String userId, String taskId) throws AppException {
        TaskDeletionResponse response = webClient.delete()
                                                 .uri(BASE_URL + SLASH + taskId)
                                                 .retrieve()
                                                 .bodyToMono(TaskDeletionResponse.class)
                                                 .block();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return null;
        }
        else if (response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return response.getDeletedTasks().get(0);
    }

    @Override
    public boolean isTaskExists(String userId, String taskId) throws AppException {
        Optional<TaskDTO> task = findTaskById(userId, taskId);
        return task.isPresent();
    }

    @Override
    public boolean toggleTask(String userId, String taskId) throws AppException {
        TaskToggleResponse response = webClient.patch()
                                                .uri(BASE_URL + SLASH + taskId + SLASH + "toggle")
                                                .retrieve()
                                                .bodyToMono(TaskToggleResponse.class)
                                                .block();
        if(response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return response.isCompleted();
    }

    @Override
    public Optional<List<TaskDTO>> findByDate(String userId, LocalDate date) throws AppException {
        StringBuffer urlBuffer = new StringBuffer(BASE_URL);
        urlBuffer.append("?dueDate=").append(date.toString());
        TaskListBodyResponse response = webClient.get()
                                                 .uri(urlBuffer.toString())
                                                 .retrieve()
                                                 .bodyToMono(TaskListBodyResponse.class)
                                                 .block();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        if(response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }

        return Optional.of(response.getTasks());
    }

    @Override
    public Optional<List<TaskDTO>> getUpcomingTasks(String userId) throws AppException {
        TaskListBodyResponse response = webClient.get()
                                                 .uri(BASE_URL + "/upcoming")
                                                 .retrieve()
                                                 .bodyToMono(TaskListBodyResponse.class)
                                                 .block();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        else if(response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return Optional.of(response.getTasks());
    }

    @Override
    public Optional<List<TaskDTO>> getThisWeekTasks(String userId) throws AppException {
        TaskListBodyResponse response = webClient.get()
                                                 .uri(BASE_URL + "/this-week")
                                                 .retrieve()
                                                 .bodyToMono(TaskListBodyResponse.class)
                                                 .block();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        else if (response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return Optional.of(response.getTasks());
    }

    @Override
    public Optional<List<TaskDTO>> getTasksOfList(String userId, String listId) throws AppException {
        TaskListBodyResponse response = webClient.get()
                                                 .uri(BASE_URL + "/list/" + listId)
                                                 .retrieve()
                                                 .bodyToMono(TaskListBodyResponse.class)
                                                 .block();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        else if (response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return Optional.of(response.getTasks());
    }

    @Override
    public Optional<List<TaskDTO>> getTasksLessThanDate(String userId, LocalDate date) throws AppException {
        TaskListBodyResponse response = webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                        .path(BASE_URL)
                                                                        .queryParam("dueDate", date)
                                                                        .queryParam("lessThan", true)
                                                                        .build()
                                                    )
                                                 .retrieve()
                                                 .bodyToMono(TaskListBodyResponse.class)
                                                 .block();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        else if (response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return Optional.of(response.getTasks());
    }
    
}
