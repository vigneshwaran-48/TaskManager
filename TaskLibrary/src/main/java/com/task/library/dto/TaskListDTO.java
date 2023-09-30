package com.task.library.dto;

public class TaskListDTO {
    
    private Long id;
    private TaskDTO taskDTO;
    private ListDTO listDTO;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
