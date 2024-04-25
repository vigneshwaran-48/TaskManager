package com.task.library.dto.list;

import java.time.LocalDateTime;
import java.util.List;

public class ListDeletionResponse {

	private String message;
	private int status;
	private LocalDateTime time;
	private List<String> deletedLists;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<String> getDeletedLists() {
		return deletedLists;
	}
	public void setDeletedLists(List<String> deletedLists) {
		this.deletedLists = deletedLists;
	}
	@Override
	public String toString() {
		return "ListCreationResponse [message=" + message + ", status=" + status + ", time=" + time + ", deletedLists="
				+ deletedLists + ", path=" + path + "]";
	}
	
	
}
