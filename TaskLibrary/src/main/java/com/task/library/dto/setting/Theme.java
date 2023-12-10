package com.task.library.dto.setting;

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
}
