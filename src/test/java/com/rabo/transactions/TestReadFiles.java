package com.rabo.transactions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.rabo.transactions.exception.RaboTransactionException;
import com.rabo.transactions.model.FailedRecords;
import com.rabo.transactions.model.Record;
import com.rabo.transactions.reader.CsvStatementReader;
import com.rabo.transactions.reader.XmlStatementReader;
import com.rabo.transactions.service.StatementRecordsValidatorServiceImpl;
import com.rabo.transactions.utils.RaboProcessor;

import junit.framework.Assert;

/**
 * @author guhans
 *
 */
public class TestReadFiles {
	
	RaboProcessor raboProcessor;
	CsvStatementReader csvStatementReader = new CsvStatementReader();
	XmlStatementReader xmlStatementReader = new XmlStatementReader();
	Record record;
	List<Record> records;
	List<FailedRecords> failed;
	
	MockMultipartFile file;
	StatementRecordsValidatorServiceImpl statementRecordsValidator;
	
	@Before
	public void pretest() {
		records = new ArrayList<>();
		failed = new ArrayList<>();
		
		raboProcessor = new RaboProcessor(csvStatementReader,xmlStatementReader);
		statementRecordsValidator = new StatementRecordsValidatorServiceImpl(raboProcessor);
	}
	
	/**
	 * This test is to verify the parsing of a valid CSV with single record failure
	 *
	 */
	@Test
	public void testReadCsv() throws IOException {
		file = new MockMultipartFile(
		        "file", 
		        "records.csv", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("Reference,AccountNumber,Description,Start Balance,Mutation,End Balance\r\n" + 
		        "194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23").getBytes()
		      );
		records = raboProcessor.getReader(FilenameUtils.getExtension(file.getOriginalFilename().toUpperCase())).readStatement(file);
		Assert.assertEquals(1, records.size());
	}
	
	/**
	 * This test is to verify the parsing of a valid XML with single record failure
	 *
	 */
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
		records = raboProcessor.getReader(FilenameUtils.getExtension(file.getOriginalFilename().toUpperCase())).readStatement(file);
		Assert.assertEquals(1, records.size());
	}
	
	/**
	 * This test is to verify the parsing of an invalid CSV
	 *
	 */
	@Test(expected = IOException.class)
	public void readInvalidCsvTest() throws IOException {
		file = new MockMultipartFile(
		        "file", 
		        "records.csv", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("Reference,AccountNumber,Description,Start Balance,Mutation,End Balance\r\n" + 
		        "194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23,51651,51651").getBytes()
		      );
		records = raboProcessor.getReader(FilenameUtils.getExtension(file.getOriginalFilename().toUpperCase())).readStatement(file);

		Assert.assertEquals(1, records.size());
	}
	
	/**
	 * This test is to verify the parsing of an invalid XML
	 *
	 */
	@Test(expected = IOException.class)
	public void readInvalidXmlTest() throws Exception {
		file = new MockMultipartFile(
		        "file", 
		        "records.xml", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("<records>" + 
		        		"<record reference=\"130498\">" + 
		        		"<accountNumber>NL69ABNA0433647324</accountNumber>" + 
		        		"<description>Tickets for Peter Theuﬂ</description>" + 
		        		"<startBalance>26.9</startBalance>" + 
		        		"<mutation>-18.78</mutation><>" + 
		        		"<endBalance>8.12</endBalance>" +
		        		"</record>" + 
		        		"</records>").getBytes("UTF-8")
		      );
		records = raboProcessor.getReader(FilenameUtils.getExtension(file.getOriginalFilename().toUpperCase())).readStatement(file);
		Assert.assertEquals(1, records.size());
	}
	
	/**
	 * This test is to verify the handling of a valid CSV with no validation failures
	 *
	 */
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
		Assert.assertEquals(0, failed.size());
	}
	
	/**
	 * This test is to verify the handling of a valid CSV with no validation failures
	 *
	 */
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
		Assert.assertEquals(0, failed.size());
	}
	
	@Test(expected = RaboTransactionException.class)
	public void readUnsupportedFile() throws Exception {
		file = new MockMultipartFile(
		        "file", 
		        "records.txt", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("test text").getBytes()
		      );
		failed = statementRecordsValidator.validate(file);
		Assert.assertEquals(0, failed.size());
	}
	
	@Test(expected = IOException.class)
	public void readEmptyCsvFile() throws Exception {
		file = new MockMultipartFile(
		        "file", 
		        "records.csv", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("").getBytes()
		      );
		failed = statementRecordsValidator.validate(file);
		Assert.assertEquals(0, failed.size());
	}
	
	@Test(expected = IOException.class)
	public void readEmptyXmlFile() throws Exception {
		file = new MockMultipartFile(
		        "file", 
		        "records.xml", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("").getBytes()
		      );
		failed = statementRecordsValidator.validate(file);
		Assert.assertEquals(0, failed.size());
	}
	
	/**
	 * This test is to verify the parsing of an invalid CSV
	 *
	 */
	@Test(expected = IOException.class)
	public void readInvalidCsvwithNullTest() throws IOException {
		file = new MockMultipartFile(
		        "file", 
		        "records.csv", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("Reference,AccountNumber,Description,Start Balance,Mutation,End Balance\r\n" + 
		        "194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23,null,51651").getBytes()
		      );
		records = raboProcessor.getReader(FilenameUtils.getExtension(file.getOriginalFilename().toUpperCase())).readStatement(file);

		Assert.assertEquals(1, records.size());
	}
	
	/**
	 * This test is to verify the parsing of an invalid XML
	 *
	 */
	@Test(expected = IOException.class)
	public void readInvalidXmlwithNullTest() throws Exception {
		file = new MockMultipartFile(
		        "file", 
		        "records.xml", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        ("<records>" + 
		        		"<record reference=\"130498\">" + 
		        		"<accountNumber>NL69ABNA0433647324</accountNumber>" + 
		        		"<description>Tickets for Peter Theuﬂ</description>" + 
		        		"<startBalance>26.9</startBalance>" + 
		        		"<mutation>null</mutation><>" + 
		        		"<endBalance>8.12</endBalance>" +
		        		"</record>" + 
		        		"</records>").getBytes("UTF-8")
		      );
		records = raboProcessor.getReader(FilenameUtils.getExtension(file.getOriginalFilename().toUpperCase())).readStatement(file);
		Assert.assertEquals(1, records.size());
	}
	
}
