package com.rabo.transactions.exception;

public class FileFormatException extends RuntimeException{

	public FileFormatException(String message) {
		super(message);
	}
	
	public FileFormatException(String message, Throwable t) {
		super(message,t);
	}
}
