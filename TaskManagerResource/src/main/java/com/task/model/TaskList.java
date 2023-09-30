package com.task.model;

import com.task.library.dto.TaskDTO;
import com.task.library.dto.TaskListDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "task_list")
public class TaskList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "task_id", nullable = false)
	private Task task;
	
	@ManyToOne
	@JoinColumn(name = "list_id", nullable = false)
	private List list;

	public Long getId() {
		return id;
	}

	public void setId(Long taskListId) {
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

	@Override
	public String toString() {
		return "TaskList [taskListId=" + id + ", task=" + task + ", list=" + list + "]";
	}
	
	public TaskListDTO toTaskListDTO() {
		
		TaskListDTO taskListDTO = new TaskListDTO();
		taskListDTO.setId(this.id);
		taskListDTO.setListDTO(this.list.toListDTO());
		taskListDTO.setTaskDTO(this.task.toTaskDTO());
		
		return taskListDTO;
	}

}
