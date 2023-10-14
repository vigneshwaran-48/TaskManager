package com.task.resource.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.resource.model.Task;
import com.task.resource.model.TaskList;

import jakarta.transaction.Transactional;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {

	Optional<List<TaskList>> findByTaskAndUserId(Task task, String userId);
	
	Optional<List<TaskList>> findByListAndUserId(com.task.resource.model.List list, String userId);
	
	Optional<List<TaskList>> findByTaskTaskIdAndUserId(Long taskId, String userId);

	Optional<TaskList> findByTaskTaskIdAndListListIdAndUserId(Long taskId, Long listId, String userId);

	@Transactional
	void deleteByTaskTaskIdAndListListIdInAndUserId(Long taskId, List<Long> listIds, String userId);

	@Transactional
	void deleteByTaskTaskIdAndUserId(Long taskId, String userId);
	
}
