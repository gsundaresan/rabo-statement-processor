/**
 * 
 */
package com.rabo.transactions.exception;

/**
 * @author guhans
 *
 */
public class RaboReadException extends RuntimeException{

	public RaboReadException(String message) {
		super(message);
	}
	
	public RaboReadException(String message, Throwable t) {
		super(message,t);
	}
}
