package com.task.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.task.library.kafka.KafkaTaskMessage;
import com.task.library.kafka.KafkaTopics;

@Component
@KafkaListener(id = "task-manager-app-listener", topics = KafkaTopics.TASK)
public class KafkaMessageListener {
    
    @Autowired SimpMessagingTemplate simpMessagingTemplate;
    
    @KafkaHandler
    public void listen(KafkaTaskMessage kafkaTaskMessage) {
 
        simpMessagingTemplate.convertAndSendToUser(
                                kafkaTaskMessage.getTaskDTO().getUserId(), 
                                "/queue/task", 
                                kafkaTaskMessage);   
    }
}
