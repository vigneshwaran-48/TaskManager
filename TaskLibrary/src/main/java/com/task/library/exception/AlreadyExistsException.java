package com.task.library.exception;

public class AlreadyExistsException extends AppException {

	private static final long serialVersionUID = 1L;
	
	public AlreadyExistsException(String message, int status) {
		super(message, status);
	}
	public AlreadyExistsException(String message) {
		super(message, 401);
	}
}
