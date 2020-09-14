package com.rabo.transactions.model;

public class FailedRecords {

	private long reference;
	
	private String description;

	public long getReference() {
		return reference;
	}

	public void setReference(long reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FailedRecords(long reference, String description) {
		super();
		this.reference = reference;
		this.description = description;
	}
	
	
	
}
