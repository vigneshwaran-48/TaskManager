package com.task.library.dto.list;

import java.time.LocalDateTime;

public class ListCreationResponse {

	private String message;
	private int status;
	private LocalDateTime time;
	private String listId;
	private String path;
	
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
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "ListCreationResponse [message=" + message + ", status=" + status + ", time=" + time + ", listId="
				+ listId + ", path=" + path + "]";
	}
	
	
}
