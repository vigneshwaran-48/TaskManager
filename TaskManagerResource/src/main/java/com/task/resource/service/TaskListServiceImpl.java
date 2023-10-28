package com.task.resource.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.library.dto.ListDTO;
import com.task.library.dto.TaskDTO;
import com.task.library.dto.TaskListDTO;
import com.task.library.exception.AppException;
import com.task.library.service.TaskListService;
import com.task.resource.model.Task;
import com.task.resource.model.TaskList;
import com.task.resource.repository.TaskListRepository;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
	private TaskListRepository taskListRepository;

    @Override
    public List<ListDTO> addListsToTask(String userId, TaskDTO task, List<ListDTO> lists, boolean removeListsNotIncluded) {

        if(userId == null) {
            throw new AppException("UserId not found in task", 400);
        }

        if(removeListsNotIncluded) {
            checkAndRemoveLists(task.getUserId(), task.getTaskId(), lists);
        }
        
        lists = getUniqueLists(task.getUserId(), task.getTaskId(), lists);
        List<TaskList> taskLists = lists
                                    .stream()
                                    .map(list -> {
                                        TaskList taskList = new TaskList();
                                        taskList.setList(com.task.resource.model.List.toList(list));
                                        taskList.setTask(Task.toTask(task));
                                        taskList.setUserId(userId);
                                        return taskList;
                                    })
                                    .toList();
        if(!taskLists.isEmpty()) {
            taskListRepository.saveAll(taskLists);
        }
        List<ListDTO> returnLists = null;
        Optional<List<TaskList>> finalTaskLists = taskListRepository.findByTaskAndUserId(Task.toTask(task), task.getUserId());

        returnLists = finalTaskLists.get().stream().map(taskList -> taskList.getList().toListDTO()).toList();
        return returnLists;
    }

    @Override
    public void deleteTaskListsRelation(String userId, Long taskId, List<Long> listIds) {
        taskListRepository.deleteByTaskTaskIdAndListListIdInAndUserId(taskId, listIds, userId);
    }  

    @Override
    public void deleteAllRelationOfTask(String userId, Long taskId) {
        taskListRepository.deleteByTaskTaskIdAndUserId(taskId, userId);
    }

    @Override
    public Optional<List<TaskListDTO>> findByList(String userId, ListDTO listDTO) {

        if(userId == null) {
            throw new AppException("UserId not found", 400);
        }

        Optional<List<TaskList>> taskLists = taskListRepository
            .findByListAndUserId(com.task.resource.model.List.toList(listDTO), userId);
        if(taskLists.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(taskLists.get()
                                    .stream()
                                    .map(taskList -> taskList.toTaskListDTO())
                                    .toList());
    }

    @Override
    public void deleteAllRelationOfList(String userId, Long listId) {
        taskListRepository.deleteByListListIdAndUserId(listId, userId);
    }

    private List<ListDTO> getUniqueLists(String userId, Long taskId, List<ListDTO> lists) {
        List<ListDTO> filteredLists = new ArrayList<>();
        if(userId == null) {
            throw new AppException("UserId not found", 400);
        }

        for(ListDTO listDTO : lists) {
            if(taskListRepository.findByTaskTaskIdAndListListIdAndUserId(taskId, 
                                                        listDTO.getListId(), userId).isEmpty()) {
                filteredLists.add(listDTO);
            }
        }
        return filteredLists;
    }

    private void checkAndRemoveLists(String userId, Long taskId, List<ListDTO> lists) {

        List<Long> listsToAdd = lists.stream().map(list -> list.getListId()).toList();

        Optional<List<TaskList>> taskLists = taskListRepository.findByTaskTaskIdAndUserId(taskId, userId);
        List<Long> listsToRemove = new LinkedList<>();

        if(taskLists.isPresent()) {
            taskLists.get().forEach(list -> {
                if(!listsToAdd.contains(list.getList().getListId())) {
                    listsToRemove.add(list.getList().getListId());
                }
            });
        }
        if(!listsToRemove.isEmpty()) {
            deleteTaskListsRelation(userId, taskId, listsToRemove);
        }
    }
    
}
