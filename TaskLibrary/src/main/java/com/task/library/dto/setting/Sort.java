package com.task.library.dto.setting;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonValue;
import com.task.library.exception.AppException;

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

    public static Sort getSortBy(int name) throws AppException {
        for(Sort sort : Sort.values()) {
            if(sort.getName() == name) {
                return sort;
            }
        }
        throw new AppException("Invalid sort id", HttpStatus.BAD_REQUEST.value());
    }
    @JsonValue
    public int getValue() {
        return name;
    }
}
