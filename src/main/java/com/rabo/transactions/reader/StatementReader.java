/**
 * 
 */
package com.rabo.transactions.reader;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rabo.transactions.model.Record;

/**
 * @author guhans
 *Base interface defining the type of Statement operations that are part of our scope, we have scope to add future implementations to child classes
 */
public interface StatementReader {
	
	List<Record> readStatement(MultipartFile file) throws IOException;
}
