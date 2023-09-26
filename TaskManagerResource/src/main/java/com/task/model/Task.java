package com.task.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.task.library.dto.TaskDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Long taskId;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Column(name = "task_name", nullable = false)
	private String taskName;
	
	@Column(nullable = true)
	private String description;
	
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	@Column(name = "due_date")
	private LocalDate dueDate;
	
	@Column(name = "parent_task")
	private Long parentTask;

	@Column(name = "is_completed", nullable = false)
	private boolean isCompleted = false;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Long getParentTask() {
		return parentTask;
	}

	public void setParentTask(Long parentTask) {
		this.parentTask = parentTask;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean completed) {
		isCompleted = completed;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName + ", description=" + description + ", dueDate="
				+ dueDate + ", parentTask=" + parentTask + "]";
	}
	
	public static Task toTask(TaskDTO taskDTO) {
		Task task = new Task();
		task.setTaskId(taskDTO.getTaskId());
		task.setDescription(taskDTO.getDescription());
		task.setDueDate(taskDTO.getDueDate());
		task.setParentTask(taskDTO.getParentTaskId());
		task.setUserId(taskDTO.getUserId());
		task.setTaskName(taskDTO.getTaskName());
		
		return task;
	}
	
}
