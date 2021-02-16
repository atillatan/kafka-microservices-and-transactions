package com.tradingcorp.producer.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.tradingcorp.model.Trade;

/**
 * @author Atilla Tanrikulu
 * 
 */
@Service
public class TradeProducerService {

	private static final Logger logger = LoggerFactory.getLogger(TradeProducerService.class);

	@Value(value = "${trade.topic.name}")
	private String tradeTopicName;

	@Autowired
	private KafkaTemplate<String, Trade> kafkaTemplate;

	// Synchronize
	public void sendMessage(Trade trade) {

		this.kafkaTemplate.send(tradeTopicName, trade);

		logger.info(String.format("Trade sent -> %s", trade));
	}
 
	// Asynchronous
	@Transactional
	public void sendMessageWithCallBack(Trade trade) {

		ListenableFuture<SendResult<String, Trade>> future = this.kafkaTemplate.send(tradeTopicName, trade);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Trade>>() {

			@Override
			public void onSuccess(SendResult<String, Trade> result) {
				logger.info("Kafka sent: " + trade + " with offset: " + result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Kafka sent error: " + trade, ex);
			}
		});
	}

	public boolean isValid(Trade trade) {

		if (validateMaturityDate(trade)) {
			if (trade.getTradeId() != null) {
				return validateVersion(trade);
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean validateVersion(Trade trade) {
		// Version validation
		if (trade.getVersion() > 0) {
			return true;
		}
		return false;
	}

	private boolean validateMaturityDate(Trade trade) {
		// Store should not allow the trade which has less maturity date then today date
		return trade.getMaturityDate().isBefore(LocalDate.now()) ? false : true;
	}

}
