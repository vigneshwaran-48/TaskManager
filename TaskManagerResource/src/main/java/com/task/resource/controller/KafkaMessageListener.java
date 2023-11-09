package com.task.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.task.library.dto.Notification;

@Component
public class KafkaMessageListener {
    
    @Autowired SimpMessagingTemplate simpMessagingTemplate;
    
    @KafkaListener(
        topics = "notification",
        groupId = "test-group-48"
    )
    public void listen(Notification notification) {
        System.out.println("Got message in consumer ...");
        System.out.println(notification);
    }
}
