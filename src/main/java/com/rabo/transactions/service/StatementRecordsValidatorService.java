package com.rabo.transactions.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rabo.transactions.model.FailedRecords;

public interface StatementRecordsValidatorService {

	List<FailedRecords>validate(MultipartFile dataFile)throws Exception;
}
