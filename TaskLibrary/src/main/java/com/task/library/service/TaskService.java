package com.task.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.task.library.dto.TaskDTO;
import com.task.library.exception.AlreadyExistsException;
import com.task.library.exception.TaskNotFoundException;

public interface TaskService {

	Optional<TaskDTO> findTaskById(String userId, Long taskId);
	
	Optional<List<TaskDTO>> listTaskOfUser(String userId);
	
	Optional<List<TaskDTO>> getAllSubTasks(String userId, Long parentTaskId);
	
	Long createTask(TaskDTO taskDTO) throws Exception;
	
	TaskDTO updateTask(TaskDTO taskDTO) throws TaskNotFoundException, AlreadyExistsException;
	
	Long deleteTask(String userId, Long taskId);
	
	boolean isTaskExists(String userId, Long taskId);

	boolean toggleTask(String userId, Long taskId) throws TaskNotFoundException;

	Optional<List<TaskDTO>> findByDate(String userId, LocalDate date);
	Optional<List<TaskDTO>> getUpcomingTasks(String userId);
}
