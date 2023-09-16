package com.task.library.dto.list;

import java.time.LocalDateTime;
import java.util.List;

import com.task.library.dto.ListDTO;

public class ListBodyListResponse {

	private String message;
	private int status;
	private LocalDateTime time;
	private List<ListDTO> lists;
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
	public List<ListDTO> getLists() {
		return lists;
	}
	public void setLists(List<ListDTO> lists) {
		this.lists = lists;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "ListBodyResponse [message=" + message + ", status=" + status + ", time=" + time + ", lists=" + lists
				+ ", path=" + path + "]";
	}
	
	
}
