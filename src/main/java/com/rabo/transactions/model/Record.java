/**
 * 
 */
package com.rabo.transactions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author guhans
 *
 */
public class Record {

	@JsonProperty("Reference")
	@JacksonXmlProperty(localName ="reference")
	private String reference;
	
	@JsonProperty("AccountNumber")
	@JacksonXmlProperty(localName = "accountNumber")
	private String accountNumber;
	
	@JsonProperty("Description")
	@JacksonXmlProperty(localName = "description")
	private String description;
	
	@JsonProperty("Start Balance")
	@JacksonXmlProperty(localName = "startBalance")
	private String startBalance;
	
	@JsonProperty("Mutation")
	@JacksonXmlProperty(localName = "mutation")
	private String mutation;
	
	@JsonProperty("End Balance")
	@JacksonXmlProperty(localName = "endBalance")
	private String endBalance;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(String startBalance) {
		this.startBalance = startBalance;
	}

	public String getMutation() {
		return mutation;
	}

	public void setMutation(String mutation) {
		this.mutation = mutation;
	}

	public String getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(String endBalance) {
		this.endBalance = endBalance;
	}

	public Record(String reference, String accountNumber, String description, String startBalance, String mutation,
			String endBalance) {
		this.reference = reference;
		this.accountNumber = accountNumber;
		this.description = description;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.endBalance = endBalance;
	}

	public Record() {
	}
	
	
	
}
