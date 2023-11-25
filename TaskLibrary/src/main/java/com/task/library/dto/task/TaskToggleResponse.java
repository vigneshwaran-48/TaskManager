package com.task.library.dto.task;

import java.time.LocalDateTime;

public class TaskToggleResponse {

    private String message;
    private int status;
    private LocalDateTime time;
    private boolean isCompleted;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "TaskToggleResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", time=" + time +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
