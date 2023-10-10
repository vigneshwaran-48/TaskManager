package com.task.client.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.task.library.dto.TaskDTO;
import com.task.library.exception.AlreadyExistsException;
import com.task.library.exception.AppException;
import com.task.library.exception.TaskNotFoundException;
import com.task.library.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public Optional<TaskDTO> findTaskById(String userId, Long taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findTaskById'");
    }

    @Override
    public Optional<List<TaskDTO>> listTaskOfUser(String userId) {
        // TODO Auto-generated method stub
        System.out.println("Get tasks ....");
        return Optional.empty();
    }

    @Override
    public Optional<List<TaskDTO>> getAllSubTasks(String userId, Long parentTaskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllSubTasks'");
    }

    @Override
    public Long createTask(TaskDTO taskDTO) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTask'");
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO, boolean removeList)
            throws TaskNotFoundException, AlreadyExistsException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTask'");
    }

    @Override
    public Long deleteTask(String userId, Long taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTask'");
    }

    @Override
    public boolean isTaskExists(String userId, Long taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isTaskExists'");
    }

    @Override
    public boolean toggleTask(String userId, Long taskId) throws TaskNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toggleTask'");
    }

    @Override
    public Optional<List<TaskDTO>> findByDate(String userId, LocalDate date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByDate'");
    }

    @Override
    public Optional<List<TaskDTO>> getUpcomingTasks(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUpcomingTasks'");
    }

    @Override
    public Optional<List<TaskDTO>> getThisWeekTasks(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getThisWeekTasks'");
    }

    @Override
    public Optional<List<TaskDTO>> getTasksOfList(String userId, Long listId) throws AppException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTasksOfList'");
    }

    @Override
    public Optional<List<TaskDTO>> getTasksLessThanDate(String userId, LocalDate date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTasksLessThanDate'");
    }
    
}
