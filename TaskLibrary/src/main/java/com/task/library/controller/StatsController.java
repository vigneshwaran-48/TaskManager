package com.task.library.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.library.dto.stats.StatsResponse;
import com.task.library.dto.stats.TotalTaskStats;
import com.task.library.dto.task.TaskDTO;
import com.task.library.exception.AppException;
import com.task.library.service.TaskService;
import com.task.library.util.AuthUtil;

@RestController
@RequestMapping("/api/v1/stats")
@CrossOrigin("*")
public class StatsController {

    private final static String NOT_AUTHENTICATED = "Not Authenticated";

    @Autowired
    private TaskService taskService;
    
    @GetMapping("task")
    public ResponseEntity<?> getTotalTaskStats(Principal principal) throws AppException {

        StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}

        TotalTaskStats stats = new TotalTaskStats();

        Optional<List<TaskDTO>> tasksOptional = taskService.listTaskOfUser(userId.toString());
        
        if(tasksOptional.isPresent() && !tasksOptional.get().isEmpty()) {

            List<TaskDTO> tasks = tasksOptional.get();

            int totalTasksCount = tasks.size();
            int tasksCompleted = 0;
            int tasksOverdue = 0;
            int tasksPending = 0;

            tasks.forEach(task -> {
                
            });
            for(TaskDTO task : tasks) {
                if(task.getIsCompleted()) {
                    tasksCompleted ++;
                }
                else if(task.getDueDate().isBefore(LocalDate.now())) {
                    tasksOverdue ++;
                }
                else {
                    tasksPending ++;
                }
            }

            stats.setTaskCompleted((tasksCompleted * 100) / totalTasksCount);
            stats.setTasksOverdue((tasksOverdue * 100) / totalTasksCount);
            stats.setTasksPending((tasksPending * 100) / totalTasksCount);
        }

        StatsResponse response = new StatsResponse();
        response.setData(stats);
        response.setTime(LocalDateTime.now());
        response.setMessage("success");
        response.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok().body(response);
    }
}
