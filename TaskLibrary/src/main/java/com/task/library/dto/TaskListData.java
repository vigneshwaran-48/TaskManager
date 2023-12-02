package com.task.library.dto;

import java.io.Serializable;
import java.util.List;

import com.task.library.dto.list.ListDTO;
import com.task.library.dto.task.TaskDTO;

public class TaskListData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String userId;
    private List<TaskDTO> tasks;
    private List<ListDTO> lists;

    public TaskListData() {}

    public TaskListData(String userId, List<TaskDTO> tasks, List<ListDTO> lists) {
        this.userId = userId;
        this.tasks = tasks;
        this.lists = lists;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public List<TaskDTO> getTasks() {
        return tasks;
    }
    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
    public List<ListDTO> getLists() {
        return lists;
    }
    public void setLists(List<ListDTO> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "TaskListData [userId=" + userId + ", tasks=" + tasks + ", lists=" + lists + "]";
    }
    
}
