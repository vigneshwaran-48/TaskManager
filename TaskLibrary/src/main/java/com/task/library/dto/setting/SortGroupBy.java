package com.task.library.dto.setting;

import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    public String getValue() {
        return name;
    }
}
