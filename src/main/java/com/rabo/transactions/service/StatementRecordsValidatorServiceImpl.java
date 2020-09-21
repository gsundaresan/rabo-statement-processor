package com.rabo.transactions.service;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.transactions.model.FailedRecords;
import com.rabo.transactions.model.Record;
import com.rabo.transactions.utils.RaboProcessor;

/**
 * @author guhans
 *
 */
@Service
public class StatementRecordsValidatorServiceImpl implements StatementRecordsValidatorService {

	RaboProcessor raboProcessor;

	@Autowired
	public StatementRecordsValidatorServiceImpl(RaboProcessor raboProcessor) {
		this.raboProcessor = raboProcessor;
	}
	
	/**
	 * @author guhans
	 *The base service function calls factory implementation RaboProcessor to identify the type of Reader for the supported file extensions(csv/xml) 
	 *and calls the validator implementation
	 */
	public List<FailedRecords>validate(MultipartFile dataFile) throws Exception{
		List<Record> records;
		records = raboProcessor.getReader(FilenameUtils.getExtension(dataFile.getOriginalFilename().toUpperCase())).readStatement(dataFile);
		return raboProcessor.validateRecords(records);
	}
	
}
