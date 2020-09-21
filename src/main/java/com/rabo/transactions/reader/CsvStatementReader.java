/**
 * 
 */
package com.rabo.transactions.reader;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.rabo.transactions.model.Record;

/**
 * @author guhans
 *Current scope only involves basic read operation of CSV file, we can add further logic/implementation based on the type of read validations
 */
@Service
public class CsvStatementReader implements StatementReader {

	/**
	 * csvMapper from Jackson library is used to read the csv file, with the assumption that the first line is Column Name.
	 *converting/transforming it to the Record List object
	 *We have selected Jackson library because of the fact that it is thread safe.
	 */
	@Override
	public List<Record> readStatement(MultipartFile csvFile) throws IOException {
		CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper mapper = new CsvMapper();
        MappingIterator<Record> readValues = mapper.reader().forType(Record.class).with(bootstrapSchema).readValues(csvFile.getInputStream());
        return readValues.readAll();
	}

}
