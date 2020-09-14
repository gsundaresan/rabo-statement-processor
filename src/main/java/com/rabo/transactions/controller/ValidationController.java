/**
 * 
 */
package com.rabo.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.transactions.exception.FileFormatException;
import com.rabo.transactions.service.StatementRecordsValidatorService;

import io.swagger.annotations.ApiOperation;

/**
 * @author guhans
 *
 */
@RestController
@RequestMapping("api/v1/statementprocessor")
public class ValidationController {
	
	@Autowired
	StatementRecordsValidatorService statementRecordsValidator;

	@PostMapping("/validateStatements")
	@ApiOperation(value = "Validate User transaction statement records")
	public ResponseEntity<Object> validateStatementRecordsCSV(@RequestParam("file") MultipartFile file) throws Exception {
		try {
			return ResponseEntity.ok(statementRecordsValidator.validate(file));	
		}
		catch (FileFormatException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
