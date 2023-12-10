package com.task.library.dto.setting;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Sort {
    NAME(1),
    CREATED_TIME(2),
    RECENTLY_CREATED(3);

    private int name;
        
    Sort(int name) {
        this.name = name;
    }
    public int getName() {
        return this.name;
    }

    @JsonValue
    public int getValue() {
        return name;
    }
}
