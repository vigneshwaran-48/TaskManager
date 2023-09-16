package com.task.library.dto;

import java.time.LocalDateTime;

public class TaskBodyResponse {

	private String message;
	private int status;
	private LocalDateTime time;
	private TaskDTO task;
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
	public TaskDTO getTask() {
		return task;
	}
	public void setTask(TaskDTO task) {
		this.task = task;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "TaskCreationResponse [message=" + message + ", status=" + status + ", task=" + task + ", time="
				+ time + "]";
	}
	
}
