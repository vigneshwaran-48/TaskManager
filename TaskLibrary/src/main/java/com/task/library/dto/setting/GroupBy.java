package com.task.library.dto.setting;

public enum GroupBy {
    CREATED_TIME("createdTime"),
    DUE_DATE("dueDate");

    private String name;

    GroupBy(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
