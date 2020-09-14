package com.rabo.transactions.exception;

/**
 * @author guhans
 *
 */
public class RaboTransactionException extends RuntimeException{

	public RaboTransactionException() {
		// TODO Auto-generated constructor stub
	}
	
	public RaboTransactionException(String message, Throwable t) {
		super(message,t);
	}
}
