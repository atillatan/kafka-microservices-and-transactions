package com.tradingcorp.consumer.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.stereotype.Service;

import com.tradingcorp.model.InvalidTradeException;
import com.tradingcorp.model.Trade;

@Service
public class TradeErrorHandler implements ErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

	public static final String HEADER_KEY_ORIGINAL_TOPIC = "ORIGINAL_TOPIC";
	public static final String HEADER_KEY_RETRY_COUNT = "RETRY_COUNT";

	@Value(value = "${trade.topic.name.dlt}")
	private String tradeTopicDltName;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void handle(Exception thrownException, ConsumerRecord<?, ?> data) {

		if (thrownException instanceof ListenerExecutionFailedException
				&& thrownException.getCause() instanceof InvalidTradeException) {

			ConsumerRecord<?, ?> cr = (ConsumerRecord<?, ?>) data;
			Trade trade = (Trade) cr.value();

			ProducerRecord<String, String> producerRecord = new ProducerRecord<>(tradeTopicDltName, trade.toString());

			Header retryCount = cr.headers().lastHeader(HEADER_KEY_RETRY_COUNT);

			if (retryCount != null) {
				producerRecord.headers().add(retryCount);
			} else {
				producerRecord.headers().add(HEADER_KEY_RETRY_COUNT, "0".getBytes(StandardCharsets.UTF_8));
			}

			producerRecord.headers().add(HEADER_KEY_ORIGINAL_TOPIC, cr.topic().getBytes(StandardCharsets.UTF_8));

			// invalid trade sent to Kafka DLT topic
			this.kafkaTemplate.send(producerRecord);			 

			logger.info(String.format("## DTL Trade sent -> %s", trade));

		} else {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(os);
			thrownException.printStackTrace(ps);
			try {
				String output = os.toString("UTF8");
				logger.error("========= Error processing message: =======" + "\n" + thrownException.getMessage() + "\n"
						+ output);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

	}

}
