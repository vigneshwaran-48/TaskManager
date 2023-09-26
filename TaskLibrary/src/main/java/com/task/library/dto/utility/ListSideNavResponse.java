package com.task.library.dto.utility;

import java.time.LocalDateTime;
import java.util.List;

public class ListSideNavResponse {
    private String message;
    private int status;
    private LocalDateTime time;
    private List<ListSideNav> listSideNavList;
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

    public List<ListSideNav> getSideNavList() {
        return listSideNavList;
    }

    public void setSideNavList(List<ListSideNav> listSideNavList) {
        this.listSideNavList = listSideNavList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
