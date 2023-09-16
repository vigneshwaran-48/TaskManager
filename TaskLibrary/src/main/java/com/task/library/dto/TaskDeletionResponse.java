package com.task.library.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TaskDeletionResponse {

	private String message;
	private int status;
	private LocalDateTime time;
	private List<Long> deletedTasks;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public List<Long> getDeletedTasks() {
		return deletedTasks;
	}
	public void setDeletedTasks(List<Long> deletedTasks) {
		this.deletedTasks = deletedTasks;
	}
	@Override
	public String toString() {
		return "TaskCreationResponse [message=" + message + ", status=" + status + ", deletedTasks=" + deletedTasks + ", time="
				+ time + "]";
	}
	
	
}
