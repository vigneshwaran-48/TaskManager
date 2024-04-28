package com.task.resource.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task.resource.model.Task;

import jakarta.transaction.Transactional;

public interface TaskRepository extends MongoRepository<Task, String> {

	Optional<Task> findByTaskNameAndUserId(String taskName, String userId);
	
	Optional<Task> findByTaskIdAndUserId(String taskId, String userId);
	
	Optional<List<Task>> findByUserId(String userId);
	Optional<List<Task>> findByUserIdAndDueDate(String userId, LocalDate dueDate);
	Optional<List<Task>>
		findByUserIdAndDueDateBetween(String userId, LocalDate from, LocalDate to);

	Optional<List<Task>> findByUserIdAndDueDateGreaterThanEqual(String userId, LocalDate dueDate);	

	Optional<List<Task>> findByUserIdAndDueDateLessThan(String userId, LocalDate dueDate);
	
	@Transactional
	List<Task> deleteByUserIdAndTaskId(String userId, String taskId);
	
	boolean existsByUserIdAndTaskId(String userId, String taskId);
}
