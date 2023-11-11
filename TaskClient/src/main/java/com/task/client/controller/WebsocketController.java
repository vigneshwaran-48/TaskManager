package com.task.client.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    
    @MessageMapping("test")
    public void test() {
        System.out.println("Testing websocket ...................");
    }
}
