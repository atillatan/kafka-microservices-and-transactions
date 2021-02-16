package com.tradingcorp.producer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.tradingcorp.producer.controller.TradeController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProducerApplicationTests {

	@Autowired
	private TradeController controller;

	@Autowired(required = true)
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void postTradeMessage() throws Exception {

		String url = "http://localhost:" + port + "/post-trade?"
				+ "tradeId=1&version=1&counterParty=CP1&bookId=b1&maturityDate=2022-08-04&createdDate=2022-08-04&expiredFlag=N";

		assertThat(this.restTemplate.postForLocation(url, String.class));
	}

	@Test
	public void postTradeMessageLoad1000() throws Exception {
		Random rand = new Random();
		String url = "http://localhost:" + port + "/post-trade?" + "tradeId=" + rand.nextInt(1000) + "&version="
				+ rand.nextInt(1000) + "&counterParty=CP-" + rand.nextInt(100) + "&bookId=b-" + rand.nextInt(100)
				+ "&maturityDate=2022-08-04&createdDate=2022-08-04&expiredFlag=N";

		for (int i = 0; i < 1000; i++) {

			this.restTemplate.postForLocation(url, String.class);
		}

	}

}