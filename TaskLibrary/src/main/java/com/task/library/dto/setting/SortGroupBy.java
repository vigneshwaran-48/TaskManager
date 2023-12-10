package com.task.library.dto.setting;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonValue;
import com.task.library.exception.AppException;

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
    public static SortGroupBy geSortGroupBy(String name) throws AppException {
        for(SortGroupBy sortGroupBy : SortGroupBy.values()) {
            if(sortGroupBy.getName().equals(name)) {
                return sortGroupBy;
            }
        }
        throw new AppException("Invalid name for SortGroupBy enum", HttpStatus.BAD_REQUEST.value());
    }

    @JsonValue
    public String getValue() {
        return name;
    }
}
