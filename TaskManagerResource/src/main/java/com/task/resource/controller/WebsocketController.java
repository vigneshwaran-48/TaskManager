package com.task.resource.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    
    @MessageMapping("/task")
    public void createTaskTest() {

        for(int i = 0; i < 10; i++) {
            System.out.println("Hi");
        }
    }
}
