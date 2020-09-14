package com.rabo.transactions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.rabo.transactions.exception.FileFormatException;
import com.rabo.transactions.model.FailedRecords;
import com.rabo.transactions.model.Record;
import com.rabo.transactions.service.StatementRecordsValidatorServiceImpl;
import com.rabo.transactions.utils.RaboProcessor;

public class TestReadFiles {
	
	RaboProcessor raboProcessor;
	Record record;
	List<Record> records;
	List<FailedRecords> failed;
	
	MockMultipartFile file;
	StatementRecordsValidatorServiceImpl statementRecordsValidator;
	
	@Before
	public void pretest() {
		records = new ArrayList<>();
		failed = new ArrayList<>();
		raboProcessor = new RaboProcessor();
		statementRecordsValidator = new StatementRecordsValidatorServiceImpl(raboProcessor);
	}
	
	@Test
	public void readCsvTest() {
		file = new MockMultipartFile(
		        "file", 
		        "records.csv", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("Reference,AccountNumber,Description,Start Balance,Mutation,End Balance\r\n" + 
		        "194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23").getBytes()
		      );
		records = raboProcessor.readCsv(file);
	}
	
	@Test
	public void readXmlTest() throws Exception {
		file = new MockMultipartFile(
		        "file", 
		        "records.xml", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("<records>" + 
		        		"<record reference=\"130498\">" + 
		        		"<accountNumber>NL69ABNA0433647324</accountNumber>" + 
		        		"<description>Tickets for Peter Theuﬂ</description>" + 
		        		"<startBalance>26.9</startBalance>" + 
		        		"<mutation>-18.78</mutation>" + 
		        		"<endBalance>8.12</endBalance>" + 
		        		"</record>" + 
		        		"</records>").getBytes("UTF-8")
		      );
		records = raboProcessor.readXml(file);
	}
	
	@Test
	public void handleCsvFile() throws Exception {
		file = new MockMultipartFile(
		        "file", 
		        "records.csv", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("Reference,AccountNumber,Description,Start Balance,Mutation,End Balance\r\n" + 
		        "194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23").getBytes()
		      );
		failed = statementRecordsValidator.validate(file);
	}
	
	@Test
	public void handleXmlFile() throws Exception {
		file = new MockMultipartFile(
		        "file", 
		        "records.xml", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("<records>" + 
		        		"<record reference=\"130498\">" + 
		        		"<accountNumber>NL69ABNA0433647324</accountNumber>" + 
		        		"<description>Tickets for Peter Theuﬂ</description>" + 
		        		"<startBalance>26.9</startBalance>" + 
		        		"<mutation>-18.78</mutation>" + 
		        		"<endBalance>8.12</endBalance>" + 
		        		"</record>" + 
		        		"</records>").getBytes("UTF-8")
		      );
		failed = statementRecordsValidator.validate(file);
	}
	
	@Test(expected = FileFormatException.class)
	public void readUnsupportedFile() throws Exception {
		file = new MockMultipartFile(
		        "file", 
		        "records.txt", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("test text").getBytes()
		      );
		failed = statementRecordsValidator.validate(file);
	}
	
}
