package com.rabo.transactions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rabo.transactions.model.FailedRecords;
import com.rabo.transactions.model.Record;
import com.rabo.transactions.reader.CsvStatementReader;
import com.rabo.transactions.reader.XmlStatementReader;
import com.rabo.transactions.utils.RaboProcessor;

import junit.framework.Assert;

public class TestRecordValidation {
	
	RaboProcessor raboProcessor;
	Record record;
	CsvStatementReader csvStatementReader = new CsvStatementReader();
	XmlStatementReader xmlStatementReader = new XmlStatementReader();
	List<Record> records;
	List<FailedRecords> failedRecords;
	
	@Before
	public void preTest() {
		raboProcessor = new RaboProcessor(csvStatementReader,xmlStatementReader);
		records = new ArrayList<>();
	}
	
	@Test
	public void validateRecordsValidCase() {
		failedRecords = new ArrayList<>();
		records.add(new Record("56788","1234","test","200.23","200","400.23"));
		records.add(new Record("56789","1235","test1","200.23","-200","0.23"));
		failedRecords = raboProcessor.validateRecords(records);
		Assert.assertEquals(failedRecords.size(), 0);
	}
	
	@Test
	public void validateRecordsNonUniqueRefcase() {
		failedRecords = new ArrayList<>();
		records.add(new Record("56788","1234","test1","200.23","200","400.23"));
		records.add(new Record("56788","1235","test2","200.23","-200","0.23"));
		records.add(new Record("567889","1235","test3","200.23","-20","180.23"));
		failedRecords = raboProcessor.validateRecords(records);
		Assert.assertEquals(failedRecords.size(), 1);
		Assert.assertEquals(failedRecords.get(0).getReference(), 56788);
		Assert.assertEquals(failedRecords.get(0).getDescription(), "test2");
	}
	
	@Test
	public void validateRecordsEndBalance() {
		failedRecords = new ArrayList<>();
		records.add(new Record("56787","1234","test1","200.23","200","400.23"));
		records.add(new Record("56788","1235","test2","200.23","-200","0.23"));
		records.add(new Record("56789","1235","test3","200.23","-20","181.23"));
		failedRecords = raboProcessor.validateRecords(records);
		Assert.assertEquals(failedRecords.size(), 1);
		Assert.assertEquals(failedRecords.get(0).getReference(), 56789);
		Assert.assertEquals(failedRecords.get(0).getDescription(), "test3");
	}
	
	@Test
	public void validateRecordsCompositeNegative() {
		failedRecords = new ArrayList<>();
		records.add(new Record("56788","1234","test1","200.23","200","400.23"));
		records.add(new Record("56788","1235","test2","200.23","-200","0.23"));
		records.add(new Record("56789","1235","test2","200.23","-200","0.23"));
		records.add(new Record("56790","1235","test3","200.23","-20","181.23"));
		failedRecords = raboProcessor.validateRecords(records);
		Assert.assertEquals(2,failedRecords.size());
		Assert.assertEquals(56788,failedRecords.get(0).getReference());
		Assert.assertEquals("test2",failedRecords.get(0).getDescription());
		Assert.assertEquals(56790,failedRecords.get(1).getReference());
		Assert.assertEquals("test3",failedRecords.get(1).getDescription());
	}

}
