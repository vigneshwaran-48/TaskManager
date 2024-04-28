package com.task.library.dto.task;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;

public class TaskCreationPayload {
	
	private String taskId;
	private String userId;
	
	@NotBlank(message = "taskName is required")
	private String taskName;
	
	private String description;
	private String parentTaskId;
	private LocalDate dueDate;
	private boolean isCompleted;
	private List<String> lists;
	private Map<String, Object> links;

	public Map<String, Object> getLinks() {
		return links;
	}

	public void setLinks(Map<String, Object> links) {
		this.links = links;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(boolean completed) {
		isCompleted = completed;
	}
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
	public String getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public List<String> getLists() {
		return lists;
	}
	public void setLists(List<String> lists) {
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
	@Override
	public String toString() {
		return "TaskDTO [taskId=" + taskId + ", taskName=" + taskName + ", parentTaskId=" + parentTaskId
				+ ", dueDate=" + dueDate + "]";
	}
    public TaskDTO toTaskDTO() {
        
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription(this.getDescription());
        taskDTO.setDueDate(this.getDueDate());
        taskDTO.setTaskId(this.getTaskId());
        taskDTO.setIsCompleted(this.isCompleted());
        taskDTO.setTaskName(this.getTaskName());
        taskDTO.setUserId(this.getUserId());

        return taskDTO;
    }
}
