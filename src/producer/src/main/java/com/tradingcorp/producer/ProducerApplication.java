package com.tradingcorp.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tradingcorp.producer.service.TradeProducerService;

/**
 * @author Atilla Tanrikulu
 * 
 */
@SpringBootApplication
public class ProducerApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(ProducerApplication.class);
	
	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(ProducerApplication.class, args);
		logger.info("Server started");
		
 
	}

}
