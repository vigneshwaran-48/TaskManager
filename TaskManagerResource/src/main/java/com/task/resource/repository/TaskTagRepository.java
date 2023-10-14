package com.task.resource.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.resource.model.Tag;
import com.task.resource.model.Task;
import com.task.resource.model.TaskTag;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {

	Optional<List<TaskTag>> findByTask(Task task);
	
	Optional<List<TaskTag>> findByTag(Tag tag);
	
}
