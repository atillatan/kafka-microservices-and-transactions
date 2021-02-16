/**
 * 
 */
package com.tradingcorp.model;

import java.time.LocalDate;

/**
 * @author Atilla Tanrikulu
 * 
 */
public class Trade {

	protected Long id;

	private String tradeId;

	private int version;

	private String counterParty;

	private String bookId;

	private LocalDate maturityDate;

	private LocalDate createdDate;

	private String expiredFlag;

	public Long getId() { 
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCounterParty() {
		return counterParty;
	}

	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getExpiredFlag() {
		return expiredFlag;
	}

	public void setExpiredFlag(String expiredFlag) {
		this.expiredFlag = expiredFlag;
	}

	@Override
	public String toString() {
		return "Trade [id=" + id + ", tradeId=" + tradeId + ", version=" + version + ", counterParty=" + counterParty
				+ ", bookId=" + bookId + ", maturityDate=" + maturityDate + ", expiredFlag=" + expiredFlag + "]";
	}

}
