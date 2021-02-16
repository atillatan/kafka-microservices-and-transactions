package com.tradingcorp.consumer.service;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tradingcorp.consumer.repository.TradeRepository;
import com.tradingcorp.model.InvalidTradeException;
import com.tradingcorp.model.Trade;

@Service
@Transactional
public class TradeConsumerServiceImpl implements TradeConsumerService {

	private final Logger logger = LoggerFactory.getLogger(TradeConsumerServiceImpl.class);

	public static final String HEADER_KEY_ORIGINAL_TOPIC = "ORIGINAL_TOPIC";
	public static final String HEADER_KEY_RETRY_COUNT = "RETRY_COUNT";

	private final TradeRepository tradeRepository;

	@Autowired
	public TradeConsumerServiceImpl(TradeRepository tradeRepository) {
		this.tradeRepository = tradeRepository;
	}

	public void saveTrade(Trade trade) {
		tradeRepository.save(trade);
		logger.info(String.format("Trade inserted to databse -> %s", trade));
	}

	public Trade findById(Long id) {
		return tradeRepository.findById(id).get();
	}

	@Transactional(rollbackFor = InvalidTradeException.class)
	@KafkaListener(topics = "${trade.topic.name}", groupId = "${spring.kafka.consumer.group-id", containerFactory = "kafkaListenerContainerFactory")
	@Override
	public void consume(Trade trade) throws InvalidTradeException {

		logger.info(String.format("Trade consume -> %s", trade));

		// Validation
		if (!isValid(trade)) {
			// KafkaConsumerConfig has custom ErrorHandler which is "TradeErrorHandler"
			// in case of InvalidTradeException
			// TradeErrorHandler will put it into "trade.topic.name.dlt"
			throw new InvalidTradeException("Store should not allow the trade which has expiredFlag=Y");
		}

		try {

			// Save Trade
			this.saveTrade(trade);
			logger.info(String.format("Trade inserted -> %s", trade));

		} catch (Exception e) {
			logger.error(String.format("Trade insert error -> %s", trade) + e.getMessage(), e);
			// KafkaConsumerConfig has custom ErrorHandler which is "TradeErrorHandler"
			// in case of InvalidTradeException
			// TradeErrorHandler will put it into "trade.topic.name.dlt"
			throw new InvalidTradeException(e.getMessage());
		}
	}


	@KafkaListener(groupId = "${trade.topic.name.dlt.group}", topics = "${trade.topic.name.dlt}", containerFactory = "kafkaListenerContainerFactory")
	@Override
	public void dltListen(ConsumerRecord<?, ?> consumerRecord) {

		// We consume invalid Trade
		// We can trigger here Roll-back operations
		logger.info("Received Invalid Trade from DLT: " + consumerRecord.value());

		Header originalTopic = consumerRecord.headers().lastHeader(HEADER_KEY_ORIGINAL_TOPIC);
		
		if (originalTopic != null) {
			String originalTopicName = new String(originalTopic.value(), StandardCharsets.UTF_8);
			logger.info("Received Invalid Trade original topic: " + originalTopicName);
		}		
		
		Header retryCount = consumerRecord.headers().lastHeader(HEADER_KEY_RETRY_COUNT);

		if (retryCount != null) {
			String count = new String(retryCount.value(), StandardCharsets.UTF_8);
			logger.info("Received Invalid Trade Retry count: " + count);			 
		}  

	}

	private boolean isValid(Trade trade) {

		// We can add here all validations

		if (trade.getExpiredFlag().equals("N")) {
			return true;
		}

// Commented for test purpose
//		if (trade.getMaturityDate().isAfter(LocalDate.now())) {
//			return true;
//		}

		return false;

	}
}
