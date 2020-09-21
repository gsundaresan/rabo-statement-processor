package com.rabo.transactions.exception;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rabo.transactions.model.ValidationResponse;
import com.rabo.transactions.utils.RaboConstants;

@RestControllerAdvice
public class RaboExceptionHandler {
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ValidationResponse> handleReaderExceptions(IOException ioException){
		return ResponseEntity.badRequest().body(new ValidationResponse(RaboConstants.RABO_READ_EXCEPTION+ ":" + ioException.getMessage(), new ArrayList<>()));
	}

	@ExceptionHandler(RaboTransactionException.class)
	public ResponseEntity<ValidationResponse> handleValidatorExceptions(RaboTransactionException rabException){
		return ResponseEntity.badRequest().body(new ValidationResponse(RaboConstants.RABO_READ_EXCEPTION+ ":" + rabException.getMessage(), new ArrayList<>()));
	}
}
