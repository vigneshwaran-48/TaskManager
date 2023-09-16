package com.task.library.dto.utility;

import com.task.library.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;

public class SideNavResponse {
    private String message;
    private int status;
    private LocalDateTime time;
    private List<SideNav> sideNavList;
    private String path;

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

    public List<SideNav> getSideNavList() {
        return sideNavList;
    }

    public void setSideNavList(List<SideNav> sideNavList) {
        this.sideNavList = sideNavList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
