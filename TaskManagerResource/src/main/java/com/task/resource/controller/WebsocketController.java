package com.task.resource.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    

    @MessageMapping("/create-task")
    public void createTaskTest() {

        System.out.println("Create task endpoint called");
    }
}
