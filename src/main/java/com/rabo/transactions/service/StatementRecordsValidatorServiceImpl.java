package com.rabo.transactions.service;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.transactions.exception.FileFormatException;
import com.rabo.transactions.model.FailedRecords;
import com.rabo.transactions.model.Record;
import com.rabo.transactions.utils.RaboConstants;
import com.rabo.transactions.utils.RaboProcessor;

/**
 * @author guhans
 *
 */
@Service
public class StatementRecordsValidatorServiceImpl implements StatementRecordsValidatorService {

	RaboProcessor raboFileReaderUtils;

	@Autowired
	public StatementRecordsValidatorServiceImpl(RaboProcessor raboFileReaderUtils) {
		this.raboFileReaderUtils = raboFileReaderUtils;
	}
	
	private static final Logger LOGGER = LogManager.getLogger(StatementRecordsValidatorServiceImpl.class);
	
	/**
	 * @author guhans
	 *The base service function validates for the supported file extension(csv/xml) if not File format(Custom) exception is logged and thrown
	 */
	public List<FailedRecords>validate(MultipartFile dataFile) throws Exception{
		List<Record> records;
		List<FailedRecords> failed;
		if(RaboConstants.CSV_EXTENSION.equalsIgnoreCase(FilenameUtils.getExtension(dataFile.getOriginalFilename()))) {
			records = raboFileReaderUtils.readCsv(dataFile);
		}else if(RaboConstants.XML_EXTENSION.equalsIgnoreCase(FilenameUtils.getExtension(dataFile.getOriginalFilename()))){
			records = raboFileReaderUtils.readXml(dataFile);
		}else {
			LOGGER.error(RaboConstants.RABO_FILEFORMAT_EXCEPTION);
			throw new FileFormatException(RaboConstants.RABO_FILEFORMAT_EXCEPTION + dataFile.getOriginalFilename(), new Exception(RaboConstants.RABO_FILEFORMAT_EXCEPTION));
		}
		failed = raboFileReaderUtils.validateRecords(records);
				
		return failed;
	}
	
}
