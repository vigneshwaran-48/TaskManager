package com.task.library.service;

import java.util.List;

import com.task.library.dto.ListDTO;
import com.task.library.dto.TaskDTO;

public interface TaskListService {
    
    /**
     * @return Returns the added lists to the task.
     */
    List<ListDTO> addListsToTask(TaskDTO task, List<ListDTO> lists);
}
