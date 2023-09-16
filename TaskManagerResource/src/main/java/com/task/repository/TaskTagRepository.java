package com.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Tag;
import com.task.model.Task;
import com.task.model.TaskTag;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {

	Optional<List<TaskTag>> findByTask(Task task);
	
	Optional<List<TaskTag>> findByTag(Tag tag);
	
}
