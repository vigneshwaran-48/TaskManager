package com.task.library.dto.task;

import java.time.LocalDateTime;
import java.util.List;

public class TaskDeletionResponse {

	private String message;
	private int status;
	private LocalDateTime time;
	private List<String> deletedTasks;
	
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
	public List<String> getDeletedTasks() {
		return deletedTasks;
	}
	public void setDeletedTasks(List<String> deletedTasks) {
		this.deletedTasks = deletedTasks;
	}
	@Override
	public String toString() {
		return "TaskCreationResponse [message=" + message + ", status=" + status + ", deletedTasks=" + deletedTasks + ", time="
				+ time + "]";
	}
	
	
}
