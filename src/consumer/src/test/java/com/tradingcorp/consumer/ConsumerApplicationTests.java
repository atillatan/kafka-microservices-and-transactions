package com.tradingcorp.consumer;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.tradingcorp.consumer.repository.TradeRepository;
import com.tradingcorp.model.Trade;

@RunWith(SpringRunner.class)
@SpringBootTest
class ConsumerApplicationTests {

	@Test
	void contextLoads() {
	}

//	@Autowired
//	private TestEntityManager entityManager;
//
//	@Autowired
//	private TradeConsumerService tradeConsumerService;

	@MockBean
	private static TradeRepository tradeRepository;

	@Test
	public void insert_test() {

		// given
		Trade trade = new Trade();
		trade.setTradeId("2");
		trade.setVersion(1);
		trade.setCounterParty("CP2");
		trade.setBookId("B2");
		trade.setMaturityDate(LocalDate.now());
		trade.setCreatedDate(LocalDate.now());
		trade.setExpiredFlag("N");

		tradeRepository.save(trade);
		tradeRepository.flush();

		// when
		Trade searchTrade = new Trade();
		searchTrade.setTradeId(trade.getTradeId());
		searchTrade.setVersion(trade.getVersion());
		Example<Trade> example = Example.of(searchTrade);
 
		Optional<Trade> actual = tradeRepository.findOne(example);
 
		assertNotNull(actual);
		//assertNotNull(actual.get());

	}
}
