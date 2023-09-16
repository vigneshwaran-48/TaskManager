package com.task.library.dto.list;

import java.time.LocalDateTime;

import com.task.library.dto.ListDTO;

public class ListBodyResponse {

	private String message;
	private int status;
	private LocalDateTime time;
	private ListDTO list;
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
	public ListDTO getList() {
		return list;
	}
	public void setList(ListDTO list) {
		this.list = list;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "ListBodyResponse [message=" + message + ", status=" + status + ", time=" + time + ", list=" + list
				+ ", path=" + path + "]";
	}
	
	
}
