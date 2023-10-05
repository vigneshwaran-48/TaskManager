package com.task.library.controller;

import com.task.library.dto.ListDTO;
import com.task.library.dto.TaskDTO;
import com.task.library.dto.utility.ListSideNav;
import com.task.library.dto.utility.ListSideNavResponse;
import com.task.library.dto.utility.SideNav;
import com.task.library.dto.utility.SideNavResponse;
import com.task.library.service.ListService;
import com.task.library.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/utility")
@CrossOrigin("*")
public class UtilityController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ListService listService;

    @GetMapping("side-nav")
    public ResponseEntity<?> getSideNav(Principal principal) {
        //Need to remove this hardcoded after spring security enabled
        //and get the user id from principal.
        String userId = "12";

        SideNav upComing = new SideNav();
        upComing.setId("side-nav-1");
        upComing.setName("Upcoming");
        upComing.setIconClassNames("fa fa-solid fa-forward");

        SideNav today = new SideNav();
        today.setId("side-nav-2");
        today.setName("Today");
        today.setIconClassNames("bi bi-list-task");

        SideNav overdue = new SideNav();
        overdue.setId("side-nav-3");
        overdue.setName("Overdue");
        overdue.setIconClassNames("fa fa-solid fa-hourglass-end");
        Optional<List<TaskDTO>> overdueTasks = taskService.getTasksLessThanDate(userId, LocalDate.now().minusDays(1));
        overdueTasks.ifPresent(overdueTask -> {
            overdueTask = overdueTask.stream().filter(task -> !task.isCompleted()).toList();
            overdue.setCount(overdueTask.size());
        });

        SideNav stickyWall = new SideNav();
        stickyWall.setId("side-nav-4");
        stickyWall.setName("Sticky Wall");
        stickyWall.setIconClassNames("bi bi-sticky");

        Optional<List<TaskDTO>> tasks = taskService.findByDate(userId, LocalDate.now());
        tasks.ifPresent(taskDTOS -> today.setCount(taskDTOS.size()));

        Optional<List<TaskDTO>> upcomingTasks = taskService.getUpcomingTasks(userId);
        upcomingTasks.ifPresent(taskDTOS -> upComing.setCount(taskDTOS.size()));

        SideNavResponse response = new SideNavResponse();
        response.setSideNavList(List.of(upComing, today, overdue, stickyWall));
        response.setPath("/api/v1/utility/side-nav");
        response.setMessage("success");
        response.setStatus(HttpStatus.OK.value());
        response.setTime(LocalDateTime.now());

        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        return ResponseEntity.ok(response);
    }

    @GetMapping("list-side-nav")
    public ResponseEntity<?> getListSideNavs(Principal principal) {

        //Need to remove this hardcoded after spring security enabled
        //and get the user id from principal.
        String userId = "12";

        Optional<List<ListDTO>> lists = listService.listAllListsOfUser(userId);

        List<ListSideNav> listSideNavs = new LinkedList<>();

        if(lists.isPresent() && lists.get().size() > 0) {
            lists.get().forEach(listDTO -> {
                ListSideNav sideNav = new ListSideNav();
                sideNav.setColor(listDTO.getListColor());
                sideNav.setName(listDTO.getListName());
                sideNav.setId("list-sidenav-" + listDTO.getListId());

                listSideNavs.add(sideNav);
            });
        }

        ListSideNavResponse response = new ListSideNavResponse();
        response.setSideNavList(listSideNavs);
        response.setPath("/api/v1/utility/list-side-nav");
        response.setMessage("success");
        response.setStatus(HttpStatus.OK.value());
        response.setTime(LocalDateTime.now());



        return ResponseEntity.ok(response);
    }
}
