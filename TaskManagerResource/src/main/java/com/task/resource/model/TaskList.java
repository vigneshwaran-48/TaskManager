package com.task.resource.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.task.library.dto.task.TaskListDTO;

@Document
public class TaskList {

	@Id
	private String id;
	
	@DocumentReference
	private Task task;
	
	@DocumentReference
	private List list;

	private String userId;

	public String getId() {
		return id;
	}

	public void setId(String taskListId) {
		this.id = taskListId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TaskList [taskListId=" + id + ", task=" + task + ", list=" + list + ", userId=" + userId + "]";
	}
	
	public TaskListDTO toTaskListDTO() {
		
		TaskListDTO taskListDTO = new TaskListDTO();
		taskListDTO.setId(this.id);
		taskListDTO.setListDTO(this.list.toListDTO());
		taskListDTO.setTaskDTO(this.task.toTaskDTO());
		taskListDTO.setUserId(this.userId);
		
		return taskListDTO;
	}

}
