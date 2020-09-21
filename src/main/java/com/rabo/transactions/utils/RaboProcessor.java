package com.rabo.transactions.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabo.transactions.exception.RaboTransactionException;
import com.rabo.transactions.model.FailedRecords;
import com.rabo.transactions.model.Record;
import com.rabo.transactions.reader.CsvStatementReader;
import com.rabo.transactions.reader.StatementReader;
import com.rabo.transactions.reader.XmlStatementReader;

/**
 * @author guhans
 *
 */
@Component
public class RaboProcessor {
	
	CsvStatementReader csvStatementReader;
	XmlStatementReader xmlStatementReader;
	
	@Autowired
	public RaboProcessor(CsvStatementReader csvStatementReader, XmlStatementReader xmlStatementReader){
		this.csvStatementReader = csvStatementReader;
		this.xmlStatementReader = xmlStatementReader;
	}
	
	
	private static final Logger LOGGER = LogManager.getLogger(RaboProcessor.class);
	
	/**
	 * Factory implementation to identify the type of reader to be loaded upon request
	 *
	 */
	public StatementReader getReader(String fileExtension) {
		switch(fileExtension) {
		case RaboConstants.CSV_EXTENSION :
			return csvStatementReader; // Here I can also return new Reader instance instead of autowired singleton instance 
//			but it will cause linear increase in number of reader instances with respect to number of requests coming in, 
//			In that case we will have to establish some kind of control by limiting the concurrency parameters
		case RaboConstants.XML_EXTENSION :
			return xmlStatementReader;
		default :
			throw new RaboTransactionException(RaboConstants.RABO_FILEFORMAT_EXCEPTION);
		}
	}
	
	/**
	 * Validation of unique references and end-balance calculation are carried out here
	 *
	 */
	public List<FailedRecords> validateRecords(List<Record> records){
		Set<String> reference = new HashSet<>();
		List<FailedRecords> failed = new ArrayList<>();
		try {
			records.forEach(r->{
				if(reference.contains(r.getReference()) || calculateFloat(Float.parseFloat(r.getStartBalance()) , Float.parseFloat(r.getMutation())) != Float.parseFloat(r.getEndBalance())) {
					failed.add(new FailedRecords(Integer.parseInt(r.getReference()), r.getDescription()));
				}
				reference.add(r.getReference());
			});
		}catch(Exception e) {
			LOGGER.error(RaboConstants.RABO_TRANSACTION_EXCEPTION+e.getMessage(), e);
			throw new RaboTransactionException(RaboConstants.RABO_TRANSACTION_EXCEPTION+e.getMessage(), e);
		}
		return failed;
	}
		
	/**
	 * Float arithmetic operations have a failure factor of two to three decimal places, hence we are providing a special workaround.
	 * Here we are removing this factor of 3 decimal places by multiplying the operands by a factor of 1000 and then nullifying the offset by dividing the result again by the same number.
	 * The offset factor can be further increased if necessary
	 */
	private float calculateFloat(float f1, float f2) {
		return ((f1*RaboConstants.FLOAT_MULTIPLICATION_FACTOR) + (f2*RaboConstants.FLOAT_MULTIPLICATION_FACTOR))/RaboConstants.FLOAT_MULTIPLICATION_FACTOR;
	}
		
}
