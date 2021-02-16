package com.tradingcorp.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.tradingcorp.model.InvalidTradeException;
import com.tradingcorp.model.Trade;

public interface TradeConsumerService {
	
	void consume(Trade trade) throws InvalidTradeException;
	
	void dltListen(ConsumerRecord<?, ?> consumerRecord);
}
