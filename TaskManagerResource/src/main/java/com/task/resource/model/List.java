package com.task.resource.model;

import com.task.library.dto.ListDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class List {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "list_id")
	private Long listId;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Column(name = "list_name", nullable = false)
	private String listName;
	
	@Column(name = "list_color")
	private String listColor;

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
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

	@Override
	public String toString() {
		return "List [listId=" + listId + ", listName=" + listName + ", listColor=" + listColor + "]";
	}
	
	public static List toList(ListDTO listDTO) {
		List list = new List();
		list.setListId(listDTO.getListId());
		list.setListName(listDTO.getListName());
		list.setListColor(listDTO.getListColor());
		list.setUserId(listDTO.getUserId());
		
		return list;
	}

	public static ListDTO toListDTO(List list) {
		
		ListDTO listDTO = new ListDTO();
		listDTO.setListId(list.getListId());
		listDTO.setUserId(list.getUserId());
		listDTO.setListName(list.getListName());
		listDTO.setListColor(list.getListColor());
		
		return listDTO;
	}

	public ListDTO toListDTO() {
		
		ListDTO listDTO = new ListDTO();
		listDTO.setListId(this.getListId());
		listDTO.setUserId(this.getUserId());
		listDTO.setListName(this.getListName());
		listDTO.setListColor(this.getListColor());
		
		return listDTO;
	}
	
}
