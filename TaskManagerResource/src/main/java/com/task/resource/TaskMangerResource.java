package com.task.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.task", "com.task.library.controller" })
public class TaskMangerResource {

	public static void main(String[] args) {
		SpringApplication.run(TaskMangerResource.class, args);
	}
}
