/**
 * 
 */
package com.rabo.transactions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.transactions.model.FailedRecords;
import com.rabo.transactions.model.ValidationResponse;
import com.rabo.transactions.service.StatementRecordsValidatorService;
import com.rabo.transactions.utils.RaboConstants;

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
	public ResponseEntity<ValidationResponse> validateStatementRecordsCSV(@RequestParam("file") MultipartFile file) throws Exception {
		List<FailedRecords> failedRecords = statementRecordsValidator.validate(file);
		return failedRecords.size() ==0 ? ResponseEntity.ok(new ValidationResponse(RaboConstants.RABO_TRANSACTION_SUCCESS_MESSAGE,statementRecordsValidator.validate(file))) : ResponseEntity.ok(new ValidationResponse(RaboConstants.RABO_TRANSACTION_FAILED_MESSAGE,statementRecordsValidator.validate(file)));	
	}
	
}
