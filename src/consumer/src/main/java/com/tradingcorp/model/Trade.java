/**
 * 
 */
package com.tradingcorp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Atilla Tanrikulu
 * 
 * 
 */
@Entity
@Table
public class Trade {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trade_seq_gen")
	@SequenceGenerator(name = "trade_seq_gen", sequenceName = "trades_id_seq")
	@Column(name = "id", updatable = false, nullable = false)
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
