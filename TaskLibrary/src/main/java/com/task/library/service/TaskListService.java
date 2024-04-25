package com.task.library.service;

import java.util.List;
import java.util.Optional;

import com.task.library.dto.list.ListDTO;
import com.task.library.dto.task.TaskDTO;
import com.task.library.dto.task.TaskListDTO;
import com.task.library.exception.AppException;

public interface TaskListService {
    
    /**
     * @return Returns the added lists to the task.
     * @throws AppException
     */
    List<ListDTO> addListsToTask(String userId, TaskDTO task, List<ListDTO> lists, boolean removeListsNotIncluded) throws AppException;

    void deleteTaskListsRelation(String userId, String taskId, List<String> listIds);

    void deleteAllRelationOfTask(String userId, String taskId);

    void deleteAllRelationOfList(String userId, String listId);

    Optional<List<TaskListDTO>> findByList(String userId, ListDTO listDTO) throws AppException;
}
