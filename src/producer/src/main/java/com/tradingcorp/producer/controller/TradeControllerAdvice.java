package com.tradingcorp.producer.controller;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tradingcorp.model.InvalidTradeException;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class TradeControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidTradeException.class)
	public ResponseEntity<String> notFoundException(final InvalidTradeException e) {			
		return error(e, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> assertionException(final IllegalArgumentException e) {
		return error(e, HttpStatus.NOT_ACCEPTABLE);
	}

	private ResponseEntity<String> error(final Exception exception, final HttpStatus httpStatus) {
		final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Error", message);

		return new ResponseEntity<String>(responseHeaders, httpStatus);
	}

}
