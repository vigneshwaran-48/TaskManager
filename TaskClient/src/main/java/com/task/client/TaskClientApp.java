package com.task.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.task", "com.task.library.controller" })
public class TaskClientApp {

	public static void main(String[] args) {
		SpringApplication.run(TaskClientApp.class, args);
	}
}
