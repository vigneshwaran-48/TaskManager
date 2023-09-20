package com.task.library.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.task.library.dto.*;
import com.task.library.exception.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.library.exception.TaskNotFoundException;
import com.task.library.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin(value = "*")
public class TaskController {

	private final static String BASE_PATH = "/api/v1/task";
	private final static String LIST_BASE_PATH = "/api/v1/list";
	@Autowired
	private TaskService taskService;
	
	@PostMapping
	public ResponseEntity<?> createTask(@Valid @RequestBody TaskDTO task, HttpServletRequest request) throws Exception {
		
		Long taskId = taskService.createTask(task);
		
		TaskCreationResponse response = new TaskCreationResponse();
		response.setMessage("Task created!");
		response.setStatus(HttpStatus.CREATED.value());
		response.setTaskId(taskId);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + taskId);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{taskId}")
	public ResponseEntity<?> getTaskById(@PathVariable Long taskId) {
		
		//Need to remove this hardcoded after spring sevurity enabled
		//and get the user id from principal.
		TaskDTO taskDTO = taskService.findTaskById("12", taskId).orElse(null);
		if(taskDTO != null) {
			fillWithLinks(taskDTO);
		}
		
		TaskBodyResponse response = new TaskBodyResponse();
		response.setMessage("success");
		response.setStatus(taskDTO != null ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTask(taskDTO);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + taskId);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllTasksOfUser() {
		//Need to remove this hardcoded after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		List<TaskDTO> tasks = taskService.listTaskOfUser(userId).orElse(null);
		
		if(tasks != null) {
			tasks.forEach(this::fillWithLinks);
		}
		
		TaskListBodyResponse response = new TaskListBodyResponse();
		response.setMessage("success");
		response.setStatus(tasks != null && !tasks.isEmpty() 
				? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTasks(tasks);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("{taskId}")
	public ResponseEntity<?> deleteTaskById(@PathVariable Long taskId) {
		//Need to remove this hardcoded after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		
		Long deletedTask = taskService.deleteTask(userId, taskId);
		
		TaskDeletionResponse response = new TaskDeletionResponse();
		response.setMessage("Deleted Task successfully!");
		response.setStatus(deletedTask != null ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setDeletedTasks(deletedTask != null ? List.of(deletedTask) : List.of());
		response.setTime(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("{taskId}")
	public ResponseEntity<?> patchUpdateTask(@PathVariable Long taskId, @RequestBody TaskDTO task)
			throws TaskNotFoundException, AlreadyExistsException {
		//Need to remove this hardcoded after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		task.setUserId(userId);
		task.setTaskId(taskId);
		
		TaskDTO updatedTask = taskService.updateTask(task);
		
		TaskBodyResponse response = new TaskBodyResponse();
		response.setMessage("Updated task successfully!");
		response.setStatus(updatedTask != null ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTask(updatedTask);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + taskId);
		
		return ResponseEntity.ok(response);
	}
	@PatchMapping("{taskId}/toggle")
	public ResponseEntity<?> toggleTaskCompleted(@PathVariable Long taskId)
			throws TaskNotFoundException {
		//Need to remove this hardcoded after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";

		boolean isCompleted = taskService.toggleTask(userId, taskId);

		TaskToggleResponse response = new TaskToggleResponse();
		response.setMessage(isCompleted
				? "Task completed successfully" : "Task uncompleted successfully");
		response.setStatus(HttpStatus.OK.value());
		response.setCompleted(isCompleted);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + taskId + "/toggle");

		return ResponseEntity.ok(response);
	}

	@GetMapping("today")
	public ResponseEntity<?> getTodayTasks() {
		//Need to remove this hardcoded after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		List<TaskDTO> tasks = taskService.findByDate(userId, LocalDate.now()).orElse(null);

		if(tasks != null) {
			tasks.forEach(this::fillWithLinks);
		}

		TaskListBodyResponse response = new TaskListBodyResponse();
		response.setMessage("success");
		response.setStatus(tasks != null && !tasks.isEmpty()
				? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTasks(tasks);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/today");

		return ResponseEntity.ok(response);
	}
	private void fillWithLinks(TaskDTO taskDTO) {
		Map<String, Object> links = new HashMap<>();
		links.put("self", BASE_PATH + "/" + taskDTO.getTaskId());
		links.put("today", BASE_PATH + "/today");
		links.put("update", BASE_PATH + "/" + taskDTO.getTaskId());
		links.put("delete", BASE_PATH + "/" + taskDTO.getTaskId());
		links.put("allLists", LIST_BASE_PATH + "/bytask/" + taskDTO.getTaskId());
		links.put("all", BASE_PATH);
		
		taskDTO.setLinks(links);
		
		if(taskDTO.getSubTasks() != null) {
			taskDTO.getSubTasks().forEach(this::fillWithLinks);
		}
	}
}
