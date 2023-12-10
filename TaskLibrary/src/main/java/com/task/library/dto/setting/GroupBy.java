package com.task.library.dto.setting;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonValue;
import com.task.library.exception.AppException;

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
    public static GroupBy getGroupBy(String name) throws AppException {
        for(GroupBy groupBy : GroupBy.values()) {
            if(groupBy.getName().equals(name)) {
                return groupBy;
            }
        }
        throw new AppException("Invalid groupBy name", HttpStatus.BAD_REQUEST.value());
    }
   
    @JsonValue
    public String getValue() {
        return name;
    }
}
