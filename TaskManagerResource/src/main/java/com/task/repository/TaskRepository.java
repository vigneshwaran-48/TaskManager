package com.task.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Task;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task, Long> {

	Optional<Task> findByTaskNameAndUserId(String taskName, String userId);
	
	Optional<Task> findByTaskIdAndUserId(Long taskId, String userId);
	
	Optional<List<Task>> findByUserId(String userId);
	Optional<List<Task>> findByUserIdAndDueDate(String userId, LocalDate dueDate);
	Optional<List<Task>>
		findByUserIdAndDueDateGreaterThanEqualAndDueDateLessThanEqual(String userId,
																	  LocalDate from, LocalDate to);

	Optional<List<Task>> findByUserIdAndDueDateGreaterThan(String userId, LocalDate dueDate);	

	Optional<List<Task>> findByUserIdAndDueDateLessThan(String userId, LocalDate dueDate);


	Optional<List<Task>> findByUserIdAndParentTask(String userId, Long parentTask);
	
	@Transactional
	List<Task> deleteByUserIdAndTaskId(String userId, Long taskId);
	
	boolean existsByUserIdAndTaskId(String userId, Long taskId);
}
