package com.rabo.transactions.utils;

public class RaboConstants {

	public static final String RABO_READ_EXCEPTION ="Exception occurred while loading object list from file: ";
	
	public static final String RABO_TRANSACTION_EXCEPTION = "Exception occurred when validating transaction statement Records: ";
	
	public static final String RABO_FILEFORMAT_EXCEPTION = "The provided file format is not supported: ";
	
	public static final String CSV_EXTENSION = "CSV";
	
	public static final String XML_EXTENSION = "XML";
	
	public static final int FLOAT_MULTIPLICATION_FACTOR = 1000; 
	
	public static final String RABO_TRANSACTION_SUCCESS_MESSAGE = "VALIDATION SUCCEEDED FOR ALL TRANSACTIONS";
	
	public static final String RABO_TRANSACTION_FAILED_MESSAGE = "VALIDATION FAILED FOR FOLLOWING TRANSACTIONS";
	
	private RaboConstants() {
		
	}
}
