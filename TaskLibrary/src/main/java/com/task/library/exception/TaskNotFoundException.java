package com.task.library.exception;

public class TaskNotFoundException extends AppException {

	private static final long serialVersionUID = -713045800241267163L;
	
	public TaskNotFoundException(String message, int status) {
		super(message, status);
	}
	public TaskNotFoundException(String message) {
		super(message, 400);
	}
}
