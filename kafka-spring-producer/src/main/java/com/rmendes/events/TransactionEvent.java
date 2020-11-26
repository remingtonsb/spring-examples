package com.rmendes.events;

import java.math.BigDecimal;

public class TransactionEvent {
	
	
	private Long originAccountNumber;
	
	private Long destinyAccountNumber;
	
	private String transaction;
	
	private BigDecimal value;

	public Long getOriginAccountNumber() {
		return originAccountNumber;
	}

	public void setOriginAccountNumber(Long originAccountNumber) {
		this.originAccountNumber = originAccountNumber;
	}

	public Long getDestinyAccountNumber() {
		return destinyAccountNumber;
	}

	public void setDestinyAccountNumber(Long destinyAccountNumber) {
		this.destinyAccountNumber = destinyAccountNumber;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	} 

}
