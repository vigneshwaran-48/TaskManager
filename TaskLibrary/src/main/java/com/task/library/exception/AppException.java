package com.task.library.exception;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int status;
	
	public AppException(String message, int status) {
		super(message);
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
}
