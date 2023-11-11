package com.task.resource.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class Logging {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    // @Around("@annotation(com.task.resource.annotation.TimeLogger)")
    public Object timeChecker(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object response = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        LOGGER.info("Method {} execution time => {}ms", joinPoint.getSignature(), executionTime);

        return response;
    }
}
