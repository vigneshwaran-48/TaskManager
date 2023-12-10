package com.task.library.dto.setting;

public enum Sort {
    NAME(1),
    CREATED_TIME(2),
    RECENTLY_CREATED(3);

    private int id;
        
    Sort(int id) {
        this.id = id;
    }
    public int getName() {
        return this.id;
    }
}
