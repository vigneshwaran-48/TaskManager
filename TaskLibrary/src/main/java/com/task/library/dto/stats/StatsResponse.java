package com.task.library.dto.stats;

import java.time.LocalDateTime;

public class StatsResponse {
    
    private Object data;
    private int status;
    private String message;
    private LocalDateTime time;

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
