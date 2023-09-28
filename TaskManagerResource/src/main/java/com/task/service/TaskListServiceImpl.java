package com.task.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.library.dto.ListDTO;
import com.task.library.dto.TaskDTO;
import com.task.library.service.TaskListService;
import com.task.model.Task;
import com.task.model.TaskList;
import com.task.repository.TaskListRepository;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
	private TaskListRepository taskListRepository;

    @Override
    public List<ListDTO> addListsToTask(TaskDTO task, List<ListDTO> lists) {
        
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
        return lists;
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
    
}
