package com.task.library.dto.task;

import java.time.LocalDateTime;

public class TaskCreationResponse {

	private String message;
	private int status;
	private LocalDateTime time;
	private Long taskId;
	private String path;
	
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
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
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "TaskCreationResponse [message=" + message + ", status=" + status + ", taskId=" + taskId + ", time="
				+ time + "]";
	}
	
	
}
