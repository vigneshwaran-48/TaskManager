package com.task.library.dto.task;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;

public class TaskCreationPayload {
	
	private Long taskId;
	private String userId;
	
	@NotBlank(message = "taskName is required")
	private String taskName;
	
	private String description;
	private Long parentTaskId;
	private LocalDate dueDate;
	private boolean isCompleted;
	private List<Long> lists;
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
	public Long getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public List<Long> getLists() {
		return lists;
	}
	public void setLists(List<Long> lists) {
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
        taskDTO.setParentTaskId(this.getParentTaskId());
        taskDTO.setTaskName(this.getTaskName());
        taskDTO.setUserId(this.getUserId());

        return taskDTO;
    }
}
