package com.task.library.dto.setting;

public enum SortGroupBy {
    ASCENDING("ascending"),
    DESCENDING("descending");

    private String name;

    SortGroupBy(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
