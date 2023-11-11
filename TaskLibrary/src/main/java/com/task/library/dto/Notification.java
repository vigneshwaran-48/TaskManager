package com.task.library.dto;

public class Notification {
    
    private String message;
    private String timestamp;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return "Notification [message=" + message + ", timestamp=" + timestamp + "]";
    }
}
