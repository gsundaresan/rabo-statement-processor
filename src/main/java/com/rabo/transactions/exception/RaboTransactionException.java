package com.rabo.transactions.exception;

/**
 * @author guhans
 *
 */
public class RaboTransactionException extends RuntimeException{

	public RaboTransactionException(String message) {
		super(message);
	}
	
	public RaboTransactionException(String message, Throwable t) {
		super(message,t);
	}
}
