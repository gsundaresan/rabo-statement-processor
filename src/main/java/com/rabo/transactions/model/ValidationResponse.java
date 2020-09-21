package com.rabo.transactions.model;

import java.util.List;
public class ValidationResponse {

	String result;
	
	List<FailedRecords> failedRecords;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<FailedRecords> getFailedRecords() {
		return failedRecords;
	}

	public void setFailedRecords(List<FailedRecords> failedRecords) {
		this.failedRecords = failedRecords;
	}

	public ValidationResponse(String result, List<FailedRecords> failedRecords) {
		this.result = result;
		this.failedRecords = failedRecords;
	}
	
	
}
