package com.tradingcorp.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.tradingcorp.model")
public class ConsumerApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(ConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
		
		logger.debug("Application started...");
	}
}
