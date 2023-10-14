package com.task.library.util;

import org.springframework.beans.factory.annotation.Value;

public class AuthUtil {

    @Value("${app.flag.isSinglUserFlow}")
    private boolean IS_SINGLE_USERFLOW; 

    @Value("${app.singleUserId}")
    private String SINGLE_USERID;

    private static AuthUtil instance;

    public static AuthUtil getInstance() {
        if(instance == null) {
            instance = new AuthUtil();
        }
        return instance;
    }
    
    public boolean isValidUserId(StringBuffer id) {
        if(IS_SINGLE_USERFLOW) {
            id.delete(0, id.length());
            id.append(SINGLE_USERID);
            return true;
        }
        else if (id != null && !id.isEmpty()) {
            return true;
        }
        return false;
    }
}
