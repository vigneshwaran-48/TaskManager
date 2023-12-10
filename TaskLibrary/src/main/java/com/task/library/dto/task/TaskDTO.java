package com.task.library.dto.task;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.task.library.dto.list.ListDTO;

import jakarta.validation.constraints.NotBlank;

public class TaskDTO implements Serializable {
	
	private Long taskId;
	private String userId;
	
	@NotBlank(message = "taskName is required")
	private String taskName;
	
	private String description;
	private LocalDate dueDate;	
	private LocalDateTime createdTime;
	private Boolean isCompleted;
	private List<ListDTO> lists;
	private Map<String, Object> links;

	public Map<String, Object> getLinks() {
		return links;
	}

	public void setLinks(Map<String, Object> links) {
		this.links = links;
	}
	public Boolean getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
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
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public List<ListDTO> getLists() {
		return lists;
	}
	public void setLists(List<ListDTO> lists) {
		this.lists = lists;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	@Override
	public String toString() {
		return "TaskDTO [taskId=" + taskId + ", taskName=" + taskName
				+ ", dueDate=" + dueDate + "]";
	}
}
