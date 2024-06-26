package com.task.library.dto.list;

import java.io.Serializable;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;

public class ListDTO implements Serializable {

	private String listId;
	@NotBlank(message = "List name is required")
	private String listName;
	private String listColor;
	private String userId;
	private int taskCount;
	private Map<String, Object> links;
	
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getListColor() {
		return listColor;
	}
	public void setListColor(String listColor) {
		this.listColor = listColor;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Map<String, Object> getLinks() {
		return links;
	}
	public void setLinks(Map<String, Object> links) {
		this.links = links;
	}
	public int getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}
	@Override
	public String toString() {
		return "ListDTO [listId=" + listId + ", listName=" + listName + ", listColor=" + listColor;
	}
}
