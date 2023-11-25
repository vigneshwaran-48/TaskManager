package com.task.library.service;

import java.util.List;
import java.util.Optional;

import com.task.library.dto.ListDTO;
import com.task.library.dto.task.TaskDTO;
import com.task.library.dto.task.TaskListDTO;
import com.task.library.exception.AppException;

public interface TaskListService {
    
    /**
     * @return Returns the added lists to the task.
     * @throws AppException
     */
    List<ListDTO> addListsToTask(String userId, TaskDTO task, List<ListDTO> lists, boolean removeListsNotIncluded) throws AppException;

    void deleteTaskListsRelation(String userId, Long taskId, List<Long> listIds);

    void deleteAllRelationOfTask(String userId, Long taskId);

    void deleteAllRelationOfList(String userId, Long listId);

    Optional<List<TaskListDTO>> findByList(String userId, ListDTO listDTO) throws AppException;
}
