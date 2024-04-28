package com.task.resource.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task.resource.model.Task;
import com.task.resource.model.TaskList;

import jakarta.transaction.Transactional;

public interface TaskListRepository extends MongoRepository<TaskList, String> {

	Optional<List<TaskList>> findByTaskAndUserId(Task task, String userId);
	
	Optional<List<TaskList>> findByListAndUserId(com.task.resource.model.List list, String userId);
	
	Optional<List<TaskList>> findByTaskTaskIdAndUserId(String taskId, String userId);

	Optional<TaskList> findByTaskTaskIdAndListListIdAndUserId(String taskId, String listId, String userId);

	@Transactional
	void deleteByTaskTaskIdAndListListIdInAndUserId(String taskId, List<String> listIds, String userId);

	@Transactional
	void deleteByTaskTaskIdAndUserId(String taskId, String userId);

	@Transactional
	void deleteByListListIdAndUserId(String listId, String userId);
	
}
