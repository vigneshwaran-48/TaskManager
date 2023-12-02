package com.task.library.kafka;

import com.task.library.dto.list.ListDTO;

public class KafkaListMessage {
    
    private KafkaAppEvent event;
    
    private ListDTO list;

    public KafkaListMessage() {}

    public KafkaListMessage(KafkaAppEvent event, ListDTO list) {
        this.event = event;
        this.list = list;
    }

    public KafkaAppEvent getEvent() {
        return event;
    }

    public void setEvent(KafkaAppEvent event) {
        this.event = event;
    }

    public ListDTO getList() {
        return list;
    }

    public void setList(ListDTO list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "KafkaTaskMessage {event=" + event + ", list=" + list + "}";
    }

    
    
}
