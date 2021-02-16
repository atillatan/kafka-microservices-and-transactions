package com.tradingcorp.model;

public class InvalidTradeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTradeException(String cause) {
		super(cause);
	}	
 
}
 