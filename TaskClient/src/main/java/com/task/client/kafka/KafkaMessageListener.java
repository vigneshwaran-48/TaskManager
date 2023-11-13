package com.task.client.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.task.library.kafka.KafkaListMessage;
import com.task.library.kafka.KafkaTaskMessage;
import com.task.library.kafka.KafkaTopics;

@Component
public class KafkaMessageListener {
    
    @Autowired SimpMessagingTemplate simpMessagingTemplate;
    
    @KafkaListener(topics = KafkaTopics.TASK, groupId = "kafka-consumers")
    public void taskListener(KafkaTaskMessage kafkaTaskMessage) {
 
        simpMessagingTemplate.convertAndSendToUser(
                                kafkaTaskMessage.getTask().getUserId(), 
                                "/personal/task", 
                                kafkaTaskMessage);   
    }

    @KafkaListener(topics = KafkaTopics.LIST, groupId = "kafka-consumers")
    public void listListener(KafkaListMessage kafkaListMessage) {

        simpMessagingTemplate.convertAndSendToUser(
            kafkaListMessage.getList().getUserId(), "/personal/list", kafkaListMessage);
    }
}
