package com.task.resource.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.task.library.dto.list.ListDTO;
import com.task.library.service.ListService;

@Component
public class DatabasePopulatorListener {

    @Autowired
    private ListService listService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabasePopulatorListener.class);
    private static final String PERSONAL_LIST_ID = "app-personal-list-1";
    private static final String WORK_LIST_ID = "app-work-list-1";
    private static final String DEFAULT_USER = "-1";
    
    @EventListener(ContextRefreshedEvent.class)
    public void populteDatabase() {
        ListDTO personalList = new ListDTO();
        personalList.setListId(PERSONAL_LIST_ID);
        personalList.setListName("Personal");
        personalList.setListColor("#fe6a6b");
        personalList.setUserId(DEFAULT_USER);

        ListDTO workList = new ListDTO();
        workList.setListId(WORK_LIST_ID);
        workList.setListName("Work");
        workList.setListColor("#db77f3");
        workList.setUserId(DEFAULT_USER);

        try {
            if (listService.findByListId(DEFAULT_USER, PERSONAL_LIST_ID).isEmpty()) {
                listService.createList(personalList);
                LOGGER.info("Created PERSONAL list!");
            } 
            if (listService.findByListId(DEFAULT_USER, WORK_LIST_ID).isEmpty()) {
                listService.createList(workList);
                LOGGER.info("Created WORK list!");
            }
            LOGGER.info("Default lists creation done!");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
