package com.tradingcorp.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingcorp.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

}
