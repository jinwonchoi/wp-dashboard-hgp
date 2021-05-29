package com.gencode.issuetool.exception;

public class MethodUnsupportableException extends RuntimeException {
	
	public MethodUnsupportableException(String message) {
		super(message);
	}
	
	public MethodUnsupportableException(String message, Throwable cause) {
		super(message, cause);
	}

}
