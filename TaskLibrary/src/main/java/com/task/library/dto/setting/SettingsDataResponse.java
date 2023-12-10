package com.task.library.dto.setting;

import java.time.LocalDateTime;

public class SettingsDataResponse {
    
    private SettingsDTO data;
    private int status;
    private String path;
    private LocalDateTime time;
    private String message;
    
    public SettingsDTO getData() {
        return data;
    }
    public void setData(SettingsDTO data) {
        this.data = data;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DataResponse [data=" + data + ", status=" + status + ", path=" + path + ", time=" + time + ", message="
                + message + "]";
    }
}
