package com.rabo.transactions.exception;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rabo.transactions.model.ValidationResponse;
import com.rabo.transactions.utils.RaboConstants;

@RestControllerAdvice
public class RaboExceptionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(RaboExceptionHandler.class);
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ValidationResponse> handleReaderExceptions(IOException ioException){
		LOGGER.error(ioException.getMessage(),ioException);
		return ResponseEntity.badRequest().body(new ValidationResponse(RaboConstants.RABO_READ_EXCEPTION+ ":" + ioException.getMessage(), new ArrayList<>()));
	}

	@ExceptionHandler(RaboTransactionException.class)
	public ResponseEntity<ValidationResponse> handleValidatorExceptions(RaboTransactionException rabException){
		LOGGER.error(rabException.getMessage(),rabException);
		return ResponseEntity.badRequest().body(new ValidationResponse(RaboConstants.RABO_READ_EXCEPTION+ ":" + rabException.getMessage(), new ArrayList<>()));
	}
}
