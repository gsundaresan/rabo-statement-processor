package com.rabo.transactions.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabo.transactions.exception.RaboReadException;
import com.rabo.transactions.exception.RaboTransactionException;
import com.rabo.transactions.model.FailedRecords;
import com.rabo.transactions.model.Record;

/**
 * @author guhans
 *
 */
@Component
public class RaboProcessor {
	
	private static final Logger LOGGER = LogManager.getLogger(RaboProcessor.class);

	/**
	 * csvMapper from Jackson library is used to read the csv file, with the assumption that the first line is Column Name.
	 *converting/transforming it to the Record List object
	 *We have selected Jackson library because of the fact that it is thread safe.
	 */
	public List<Record> readCsv(MultipartFile csvFile){
		try {
	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
	        CsvMapper mapper = new CsvMapper();
	        MappingIterator<Record> readValues = mapper.reader().forType(Record.class).with(bootstrapSchema).readValues(csvFile.getInputStream());
	        return readValues.readAll();
	    } catch (Exception e) {
	    	LOGGER.error(RaboConstants.RABO_READ_EXCEPTION + csvFile.getName(), e);
	        throw new RaboReadException(RaboConstants.RABO_READ_EXCEPTION + csvFile.getName(),e);
	    }
	}

	/**
	 * xmlMapper from Jackson library is used to read the xml file, converting/transforming it to the Record List object.
	 *
	 */
	public List<Record> readXml(MultipartFile xmlFile){
		try {
			XmlMapper xmlMapper = new XmlMapper();
			MappingIterator<Record> readValues =  xmlMapper.readerFor(Record.class).readValues(xmlFile.getInputStream());
	        return readValues.readAll();
	    } catch (Exception e) {
	    	LOGGER.error(RaboConstants.RABO_READ_EXCEPTION + xmlFile.getName(), e);
	        throw new RaboReadException(RaboConstants.RABO_READ_EXCEPTION + xmlFile.getName(),e);
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
				if(reference.contains(r.getReference())) {
					failed.add(new FailedRecords(Integer.parseInt(r.getReference()), r.getDescription()));
				}else if(calculateFloat(Float.parseFloat(r.getStartBalance()) , Float.parseFloat(r.getMutation())) != Float.parseFloat(r.getEndBalance())) {
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
	 * Here we are removing this factor of 3 decimal places by mutiplying the operands by a factor of 1000 and then nullifying the offset by dividing the result again by the same number.
	 * The offset factor can be further increased if necessary
	 */
	private float calculateFloat(float f1, float f2) {
		return ((f1*RaboConstants.FLOAT_MULTIPLICATION_FACTOR) + (f2*RaboConstants.FLOAT_MULTIPLICATION_FACTOR))/RaboConstants.FLOAT_MULTIPLICATION_FACTOR;
	}
		
}
