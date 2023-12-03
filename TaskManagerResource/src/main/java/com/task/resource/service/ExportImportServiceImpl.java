package com.task.resource.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.task.library.dto.TaskListData;
import com.task.library.dto.list.ListDTO;
import com.task.library.dto.task.TaskDTO;
import com.task.library.exception.AlreadyExistsException;
import com.task.library.exception.AppException;
import com.task.library.exception.TaskNotFoundException;
import com.task.library.service.ExportImportService;
import com.task.library.service.ListService;
import com.task.library.service.TaskService;

@Service
public class ExportImportServiceImpl implements ExportImportService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExportImportServiceImpl.class); 
    @Autowired
    private TaskService taskService;

    @Autowired
    private ListService listService;

    @Override
    public Optional<byte[]> exportData(String userId) throws AppException {
        List<TaskDTO> tasks = taskService.listTaskOfUser(userId).orElse(null);
        List<ListDTO> lists = listService.listAllListsOfUser(userId).orElse(null);

        if(tasks == null && tasks == null) {
            return Optional.empty();
        }

        TaskListData data = new TaskListData(userId, tasks, lists);
        
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream)) {
            objectOutputStream.writeObject(data);
        }
        catch(IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("Error while exporting data", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return Optional.of(arrayOutputStream.toByteArray());
    }

    @Override
    public void importData(String userId, byte[] data) throws AppException {

        try(ObjectInputStream objectOutputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            TaskListData taskListData = (TaskListData) objectOutputStream.readObject();

            if(!taskListData.getUserId().equals(userId)) {
                throw new AppException("This data is not yours!", HttpStatus.UNAUTHORIZED.value());
            }
            /**
             * This order is important because when task is first imported then sometimes it has a relation
             * with a list that dosen't imported yet.
             */
            addOrUpdateLists(userId, taskListData.getLists());
            addOrUpdateTasks(userId, taskListData.getTasks());
            LOGGER.info("Imported data");
            
        }
        catch(IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("Error while parsing import data", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("Error while importing data", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
    }

    private void addOrUpdateTasks(String userId, List<TaskDTO> tasks) throws Exception {
        for(TaskDTO task : tasks) {
            taskService.updateTask(task, true, false);
        }
    }

    private void addOrUpdateLists(String userId, List<ListDTO> lists) throws Exception {
        for(ListDTO list : lists) {
            listService.updateList(list, false);
        }
    }
    
}
