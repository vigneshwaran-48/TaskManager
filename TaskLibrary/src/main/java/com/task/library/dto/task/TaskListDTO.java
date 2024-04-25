package com.task.library.dto.task;

import com.task.library.dto.list.ListDTO;

public class TaskListDTO {
    
    private String id;
    private String userId;
    private TaskDTO taskDTO;
    private ListDTO listDTO;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public TaskDTO getTaskDTO() {
        return taskDTO;
    }
    public void setTaskDTO(TaskDTO taskDTO) {
        this.taskDTO = taskDTO;
    }
    public ListDTO getListDTO() {
        return listDTO;
    }
    public void setListDTO(ListDTO listDTO) {
        this.listDTO = listDTO;
    }

    
}
