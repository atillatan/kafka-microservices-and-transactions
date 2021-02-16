package com.tradingcorp.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import com.tradingcorp.model.Trade;

/**
 * @author Atilla Tanrikulu
 * 
 */
@Configuration
public class KafkaProducerConfig {

	@Value(value = "${spring.kafka.producer.bootstrap-servers}")
	private String bootstrapAddress;

	@Bean
	public ProducerFactory<String, Trade> tradeProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		DefaultKafkaProducerFactory<String, Trade> factory = new DefaultKafkaProducerFactory<>(configProps);
		factory.transactionCapable();
		factory.setTransactionIdPrefix("tran-");

		return factory;
	}

	@Bean
	public KafkaTransactionManager<String, Trade> transactionManager(ProducerFactory<String, Trade> producerFactory) {
		KafkaTransactionManager<String, Trade> manager = new KafkaTransactionManager<String, Trade>(producerFactory);
		return manager;
	}

	@Bean
	public KafkaTemplate<String, Trade> tradeKafkaTemplate() {
		return new KafkaTemplate<>(tradeProducerFactory());
	}
}
