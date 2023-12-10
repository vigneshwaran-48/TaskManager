package com.task.library.dto.setting;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Theme {
    DARK("dark"),
    LIGHT("light");

    private String name;
        
    Theme(String name) {
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
