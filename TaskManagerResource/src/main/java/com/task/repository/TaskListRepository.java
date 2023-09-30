package com.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Task;
import com.task.model.TaskList;

import jakarta.transaction.Transactional;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {

	Optional<List<TaskList>> findByTask(Task task);
	
	Optional<List<TaskList>> findByList(com.task.model.List list);
	
	Optional<List<TaskList>> findByTaskTaskId(Long taskId);

	Optional<TaskList> findByTaskTaskIdAndListListId(Long taskId, Long listId);

	@Transactional
	void deleteByTaskTaskIdAndListListIdIn(Long taskId, List<Long> listIds);

	@Transactional
	void deleteByTaskTaskId(Long taskId);
	
}
