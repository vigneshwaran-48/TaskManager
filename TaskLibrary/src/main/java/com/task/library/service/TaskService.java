package com.task.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.task.library.dto.task.TaskDTO;
import com.task.library.exception.AppException;
import com.task.library.exception.TaskNotFoundException;

public interface TaskService {

	Optional<TaskDTO> findTaskById(String userId, String taskId) throws AppException;
	
	Optional<List<TaskDTO>> listTaskOfUser(String userId) throws AppException;
		
	String createTask(TaskDTO taskDTO) throws Exception;
	
	TaskDTO updateTask(TaskDTO taskDTO, boolean removeList) throws AppException;
	
	String deleteTask(String userId, String taskId) throws AppException;
	
	boolean isTaskExists(String userId, String taskId) throws AppException;

	boolean toggleTask(String userId, String taskId) throws TaskNotFoundException, AppException;

	Optional<List<TaskDTO>> findByDate(String userId, LocalDate date) throws AppException;

	Optional<List<TaskDTO>> getUpcomingTasks(String userId) throws AppException;

	Optional<List<TaskDTO>> getThisWeekTasks(String userId) throws AppException;

	Optional<List<TaskDTO>> getTasksOfList(String userId, String listId) throws AppException;

	Optional<List<TaskDTO>> getTasksLessThanDate(String userId, LocalDate date) throws AppException;
}
