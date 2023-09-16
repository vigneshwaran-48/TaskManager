package com.task.library.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TaskListBodyResponse {

	private String message;
	private int status;
	private LocalDateTime time;
	private List<TaskDTO> tasks;
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
	public List<TaskDTO> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskDTO> tasks) {
		this.tasks = tasks;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "TaskCreationResponse [message=" + message + ", status=" + status + ", tasks=" + tasks + ", time="
				+ time + "]";
	}
	
}
