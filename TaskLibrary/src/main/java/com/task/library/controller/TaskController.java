package com.task.library.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.task.library.dto.*;
import com.task.library.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.library.service.ListService;
import com.task.library.service.TaskService;
import com.task.library.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

	private final static String BASE_PATH = "/api/v1/task";
	private final static String LIST_BASE_PATH = "/api/v1/list";
	private final static String NOT_AUTHENTICATED = "Not Authenticated";

	@Autowired
	private TaskService taskService;

	@Autowired
	private ListService listService;
	
	@PostMapping
	public ResponseEntity<?> createTask(@Valid @RequestBody TaskCreationPayload task, 
			HttpServletRequest request, Principal principal) throws Exception {
		
		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		task.setUserId(userId.toString());

		TaskDTO taskDTO = task.toTaskDTO();

		if(task.getLists() != null) {
			
			List<ListDTO> lists = task.getLists().stream().map(list -> {
				return listService.findByListId(task.getUserId(), list).get();
			}).toList();

			taskDTO.setLists(lists);
		}

		Long taskId = taskService.createTask(taskDTO);
		
		TaskCreationResponse response = new TaskCreationResponse();
		response.setMessage("Task created!");
		response.setStatus(HttpStatus.CREATED.value());
		response.setTaskId(taskId);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + taskId);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{taskId}")
	public ResponseEntity<?> getTaskById(@PathVariable Long taskId, Principal principal) throws AppException {
		
		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}

		TaskDTO taskDTO = taskService.findTaskById(userId.toString(), taskId).orElse(null);
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
	public ResponseEntity<?> getAllTasksOfUser(
									@RequestParam Optional<LocalDate> dueDate, 
									@RequestParam Optional<Boolean> lessThan,
									Principal principal
								) throws AppException {

		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		List<TaskDTO> tasks;

		if(dueDate.isPresent()) {
			if(lessThan.isPresent() && lessThan.get()) {
				tasks = taskService.getTasksLessThanDate(
										userId.toString(), 
										dueDate.get()
									).orElse(null);
			}
			else {
				tasks = taskService.findByDate(userId.toString(), dueDate.get()).orElse(null);
			}
		}
		else {
			tasks = taskService.listTaskOfUser(userId.toString()).orElse(null);
		}
		
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
	public ResponseEntity<?> deleteTaskById(@PathVariable Long taskId, Principal principal) throws AppException {

		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		
		Long deletedTask = taskService.deleteTask(userId.toString(), taskId);
		
		TaskDeletionResponse response = new TaskDeletionResponse();
		response.setMessage("Deleted Task successfully!");
		response.setStatus(deletedTask != null ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setDeletedTasks(deletedTask != null ? List.of(deletedTask) : List.of());
		response.setTime(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("{taskId}")
	public ResponseEntity<?> patchUpdateTask(@PathVariable Long taskId, @RequestBody TaskDTO task, 
											 @RequestParam(
												defaultValue = "false", 
												required = false
											 ) String removeListNotIncluded,
											 Principal principal)
			throws AppException {

		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		task.setUserId(userId.toString());
		task.setTaskId(taskId);
		
		TaskDTO updatedTask = taskService.updateTask(task, Boolean.parseBoolean(removeListNotIncluded));
		
		TaskBodyResponse response = new TaskBodyResponse();
		response.setMessage("Updated task successfully!");
		response.setStatus(updatedTask != null ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTask(updatedTask);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + taskId);
		
		return ResponseEntity.ok(response);
	}
	@PatchMapping("{taskId}/toggle")
	public ResponseEntity<?> toggleTaskCompleted(@PathVariable Long taskId, Principal principal)
			throws AppException {

		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}

		boolean isCompleted = taskService.toggleTask(userId.toString(), taskId);

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
	public ResponseEntity<?> getTodayTasks(Principal principal) throws AppException {

		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		List<TaskDTO> tasks = taskService.findByDate(userId.toString(), LocalDate.now()).orElse(null);

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
	@GetMapping("upcoming")
	public ResponseEntity<?> getUpcomingTasks(Principal principal) throws AppException {

		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		Optional<List<TaskDTO>> tasks = taskService.getUpcomingTasks(userId.toString());

		tasks.ifPresent(taskDTOS -> taskDTOS.forEach(this::fillWithLinks));

		TaskListBodyResponse response = new TaskListBodyResponse();
		response.setMessage("success");
		response.setStatus(tasks.isPresent() && !tasks.get().isEmpty()
				? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTasks(tasks.orElse(null));
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/upcoming");


		return ResponseEntity.ok(response);
	}
	@GetMapping("this-week")
	public ResponseEntity<?> getThisWeekTasks(Principal principal) throws AppException {

		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		Optional<List<TaskDTO>> tasks = taskService.getThisWeekTasks(userId.toString());

		tasks.ifPresent(taskDTOS -> taskDTOS.forEach(this::fillWithLinks));

		TaskListBodyResponse response = new TaskListBodyResponse();
		response.setMessage("success");
		response.setStatus(tasks.isPresent() && !tasks.get().isEmpty()
				? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTasks(tasks.orElse(null));
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/this-week");


		return ResponseEntity.ok(response);
	}

	@GetMapping("overdue")
	public ResponseEntity<?> getOverduedTasks(Principal principal) throws AppException {

		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		List<TaskDTO> tasks = taskService.getTasksLessThanDate(userId.toString(), 
													LocalDate.now().minusDays(1)).orElse(null);

		if(tasks != null) {
			tasks = tasks.stream().filter(task -> !task.getIsCompleted()).toList();
			tasks.forEach(this::fillWithLinks);
		}

		TaskListBodyResponse response = new TaskListBodyResponse();
		response.setMessage("success");
		response.setStatus(tasks != null && !tasks.isEmpty()
				? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTasks(tasks);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/overdue");


		return ResponseEntity.ok(response);
	}

	@GetMapping("list/{listId}")
	public ResponseEntity<?> getAllTasksOfList(@PathVariable Long listId, Principal principal) throws AppException {

		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}

		List<TaskDTO> tasks = taskService.getTasksOfList(userId.toString(), listId).orElse(null);

		if(tasks != null) {
			tasks.forEach(this::fillWithLinks);
		}

		TaskListBodyResponse response = new TaskListBodyResponse();
		response.setMessage("success");
		response.setStatus(tasks != null && !tasks.isEmpty()
				? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTasks(tasks);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/list/" + listId);

		return ResponseEntity.ok(response);
	}

	@GetMapping("search")
	public ResponseEntity<?> searchTask(@RequestParam String taskName, Principal principal) throws AppException {
		
		StringBuffer userId = new StringBuffer(principal.getName());
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}

		List<TaskDTO> filteredTasks = new LinkedList<>();

		Optional<List<TaskDTO>> tasksOptional = taskService.listTaskOfUser(userId.toString());
		tasksOptional.ifPresent(tasks -> {
			tasks.forEach(task -> {
				if(task.getTaskName().toLowerCase().contains(taskName.toLowerCase())) {
					filteredTasks.add(task);
				}
			});
		});
		TaskListBodyResponse response = new TaskListBodyResponse();
		response.setMessage("success");
		response.setStatus(filteredTasks != null && !filteredTasks.isEmpty()
				? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setTasks(filteredTasks);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/search");
		
		return ResponseEntity.ok(response);
	}
	private void fillWithLinks(TaskDTO taskDTO) {
		Map<String, Object> links = new HashMap<>();
		links.put("self", BASE_PATH + "/" + taskDTO.getTaskId());
		links.put("today", BASE_PATH + "/today");
		links.put("upcoming", BASE_PATH + "/upcoming");
		links.put("thisWeek", BASE_PATH + "/this-week");
		links.put("update", BASE_PATH + "/" + taskDTO.getTaskId());
		links.put("delete", BASE_PATH + "/" + taskDTO.getTaskId());
		links.put("allLists", LIST_BASE_PATH + "/bytask/" + taskDTO.getTaskId());
		links.put("search", BASE_PATH + "/search");
		links.put("all", BASE_PATH);
		
		taskDTO.setLinks(links);
		
		if(taskDTO.getSubTasks() != null) {
			taskDTO.getSubTasks().forEach(this::fillWithLinks);
		}
	}
}
