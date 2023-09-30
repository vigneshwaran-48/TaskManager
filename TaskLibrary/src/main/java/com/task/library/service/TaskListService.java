package com.task.library.service;

import java.util.List;
import java.util.Optional;

import com.task.library.dto.ListDTO;
import com.task.library.dto.TaskDTO;
import com.task.library.dto.TaskListDTO;

public interface TaskListService {
    
    /**
     * @return Returns the added lists to the task.
     */
    List<ListDTO> addListsToTask(TaskDTO task, List<ListDTO> lists, boolean removeListsNotIncluded);

    void deleteTaskListsRelation(Long taskId, List<Long> listIds);

    void deleteAllRelationOfTask(Long taskId);

    Optional<List<TaskListDTO>> findByList(ListDTO listDTO);
}
