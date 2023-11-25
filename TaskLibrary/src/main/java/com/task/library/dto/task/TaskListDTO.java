package com.task.library.dto.task;

import com.task.library.dto.ListDTO;

public class TaskListDTO {
    
    private Long id;
    private String userId;
    private TaskDTO taskDTO;
    private ListDTO listDTO;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
