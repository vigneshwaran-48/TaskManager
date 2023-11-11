package com.task.library.kafka;

import com.task.library.dto.TaskDTO;

public class KafkaTaskMessage {
    
    private KafkaAppEvent event;
    
    private TaskDTO task;

    public KafkaTaskMessage() {}

    public KafkaTaskMessage(KafkaAppEvent event, TaskDTO task) {
        this.event = event;
        this.task = task;
    }

    public KafkaAppEvent getEvent() {
        return event;
    }

    public void setEvent(KafkaAppEvent event) {
        this.event = event;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "KafkaTaskMessage {event=" + event + ", task=" + task + "}";
    }

    
    
}
