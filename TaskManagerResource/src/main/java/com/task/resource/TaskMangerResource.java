package com.task.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.task.resource", "com.task.library.controller", "com.task.library.advice" })
public class TaskMangerResource {
	
	public static void main(String[] args) {
		SpringApplication.run(TaskMangerResource.class, args);
	}
}
