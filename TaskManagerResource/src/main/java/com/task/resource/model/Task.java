package com.task.resource.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.task.library.dto.task.TaskDTO;

@Document
public class Task {

	@Id
	private String taskId;
	
	private String userId;
	
	private String taskName;
	
	private String description;
	
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private LocalDate dueDate;

	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private LocalDateTime createdTime = LocalDateTime.now();
	
	private boolean isCompleted = false;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName + ", description=" + description + ", dueDate="
				+ dueDate + "]";
	}
	
	public static Task toTask(TaskDTO taskDTO) {
		Task task = new Task();
		task.setTaskId(taskDTO.getTaskId());
		task.setDescription(taskDTO.getDescription());
		task.setDueDate(taskDTO.getDueDate());
		task.setUserId(taskDTO.getUserId());
		task.setTaskName(taskDTO.getTaskName());
		task.setIsCompleted(taskDTO.getIsCompleted());
		task.setCreatedTime(taskDTO.getCreatedTime());
		
		return task;
	}
	
	public TaskDTO toTaskDTO() {

		TaskDTO taskDTO = new TaskDTO();
		
		taskDTO.setUserId(this.getUserId());
		taskDTO.setDescription(this.getDescription());
		taskDTO.setTaskId(this.getTaskId());
		taskDTO.setTaskName(this.getTaskName());
		taskDTO.setDueDate(this.getDueDate());
		taskDTO.setIsCompleted(this.getIsCompleted());
		taskDTO.setCreatedTime(this.createdTime);

		return taskDTO;
	}
}
