package com.tradingcorp.producer.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tradingcorp.model.InvalidTradeException;
import com.tradingcorp.model.Trade;
import com.tradingcorp.producer.service.TradeProducerService;


/**
 * @author Atilla Tanrikulu1
 * 
 */
@RestController
public class TradeController {

	private final Logger logger = LoggerFactory.getLogger(TradeController.class);

	private TradeProducerService tradeService;

	@Autowired
	public TradeController(TradeProducerService tradeService) {
		this.tradeService = tradeService;
	}

	@PostMapping(path = "/post-trade")
	public void sendTrade(@RequestParam("tradeId") String tradeId, 
			@RequestParam("version") int version,
			@RequestParam("counterParty") String counterParty, 
			@RequestParam("bookId") String bookId,
			@RequestParam("maturityDate") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maturityDate,
			@RequestParam("createdDate") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate,
			@RequestParam("expiredFlag") String expiredFlag) throws InvalidTradeException{

		Trade trade = new Trade();
		trade.setTradeId(tradeId);
		trade.setVersion(version);
		trade.setCounterParty(counterParty);
		trade.setBookId(bookId);
		trade.setMaturityDate(maturityDate);
		trade.setCreatedDate(createdDate);
		trade.setExpiredFlag(expiredFlag);

		logger.info(String.format("RESTful request -> %s", trade));

		// Validation applied
		if (tradeService.isValid(trade)) {
			logger.info("Trade is VALID trade:" + trade.toString());
			this.tradeService.sendMessageWithCallBack(trade);

		} else {
			// look for TradeControllerAdvice
			logger.error("ERROR tradeid:" + trade.getTradeId()
					+ " Store should not allow the trade which has less maturity date then today date");
			throw new InvalidTradeException("ERROR tradeid:" + trade.getTradeId()
					+ " Store should not allow the trade which has less maturity date then today date");
		}

	}

}
