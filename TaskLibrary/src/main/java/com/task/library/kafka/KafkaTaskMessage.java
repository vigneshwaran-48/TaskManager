package com.task.library.kafka;

import com.task.library.dto.TaskDTO;

public class KafkaTaskMessage {
    
    private KafkaAppEvent event;
    
    private TaskDTO taskDTO;

    public KafkaTaskMessage() {}

    public KafkaTaskMessage(KafkaAppEvent event, TaskDTO taskDTO) {
        this.event = event;
        this.taskDTO = taskDTO;
    }

    public KafkaAppEvent getEvent() {
        return event;
    }

    public void setEvent(KafkaAppEvent event) {
        this.event = event;
    }

    public TaskDTO getTaskDTO() {
        return taskDTO;
    }

    public void setTaskDTO(TaskDTO taskDTO) {
        this.taskDTO = taskDTO;
    }

    @Override
    public String toString() {
        return "KafkaTaskMessage {event=" + event + ", taskDTO=" + taskDTO + "}";
    }

    
    
}
