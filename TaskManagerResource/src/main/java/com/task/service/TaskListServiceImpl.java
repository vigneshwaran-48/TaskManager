package com.task.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.library.dto.ListDTO;
import com.task.library.dto.TaskDTO;
import com.task.library.dto.TaskListDTO;
import com.task.library.service.TaskListService;
import com.task.model.Task;
import com.task.model.TaskList;
import com.task.repository.TaskListRepository;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
	private TaskListRepository taskListRepository;

    @Override
    public List<ListDTO> addListsToTask(TaskDTO task, List<ListDTO> lists, boolean removeListsNotIncluded) {

        if(removeListsNotIncluded) {
            checkAndRemoveLists(task.getTaskId(), lists);
        }
        
        lists = getUniqueLists(task.getTaskId(), lists);
        List<TaskList> taskLists = lists
                                    .stream()
                                    .map(list -> {
                                        TaskList taskList = new TaskList();
                                        taskList.setList(com.task.model.List.toList(list));
                                        taskList.setTask(Task.toTask(task));
                                        return taskList;
                                    })
                                    .toList();
        if(!taskLists.isEmpty()) {
            taskListRepository.saveAll(taskLists);
        }
        List<ListDTO> returnLists = null;
        Optional<List<TaskList>> finalTaskLists = taskListRepository.findByTask(Task.toTask(task));

        returnLists = finalTaskLists.get().stream().map(taskList -> taskList.getList().toListDTO()).toList();
        return returnLists;
    }

    @Override
    public void deleteTaskListsRelation(Long taskId, List<Long> listIds) {
        taskListRepository.deleteByTaskTaskIdAndListListIdIn(taskId, listIds);
    }  

    @Override
    public void deleteAllRelationOfTask(Long taskId) {
        taskListRepository.deleteByTaskTaskId(taskId);
    }

    @Override
    public Optional<List<TaskListDTO>> findByList(ListDTO listDTO) {

        Optional<List<TaskList>> taskLists = taskListRepository.findByList(com.task.model.List.toList(listDTO));
        if(taskLists.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(taskLists.get()
                                    .stream()
                                    .map(taskList -> taskList.toTaskListDTO())
                                    .toList());
    }

    private List<ListDTO> getUniqueLists(Long taskId, List<ListDTO> lists) {
        List<ListDTO> filteredLists = new ArrayList<>();

        for(ListDTO listDTO : lists) {
            if(taskListRepository.findByTaskTaskIdAndListListId(taskId, listDTO.getListId()).isEmpty()) {
                filteredLists.add(listDTO);
            }
        }
        return filteredLists;
    }

    private void checkAndRemoveLists(Long taskId, List<ListDTO> lists) {

        List<Long> listsToAdd = lists.stream().map(list -> list.getListId()).toList();

        Optional<List<TaskList>> taskLists = taskListRepository.findByTaskTaskId(taskId);
        List<Long> listsToRemove = new LinkedList<>();

        if(taskLists.isPresent()) {
            taskLists.get().forEach(list -> {
                if(!listsToAdd.contains(list.getList().getListId())) {
                    listsToRemove.add(list.getList().getListId());
                }
            });
        }
        if(!listsToRemove.isEmpty()) {
            deleteTaskListsRelation(taskId, listsToRemove);
        }
    }
    
}
