package com.rabo.transactions.exception;

public class FileFormatException extends RuntimeException{

	public FileFormatException() {
		
	}
	
	public FileFormatException(String message, Throwable t) {
		super(message,t);
	}
}
