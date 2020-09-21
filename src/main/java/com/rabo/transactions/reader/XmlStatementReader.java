package com.rabo.transactions.reader;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabo.transactions.model.Record;

/**
 * Current scope only involves basic read operation of Xml file, we can add further logic/implementation based on the type of read validations
 *
 */

@Service
public class XmlStatementReader implements StatementReader {

	/**
	 * xmlMapper from Jackson library is used to read the xml file, converting/transforming it to the Record List object.
	 *
	 */
	@Override
	public List<Record> readStatement(MultipartFile xmlFile) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		MappingIterator<Record> readValues =  xmlMapper.readerFor(Record.class).readValues(xmlFile.getInputStream());
        return readValues.readAll();
	}

}
