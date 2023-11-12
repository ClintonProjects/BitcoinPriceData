package com.bitcoinprice.analytics.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bitcoinprice.dataparsing.coindata.ExchangeDataRecieved;

@Repository
@Transactional
public interface BitcoinPriceData  extends MongoRepository<ExchangeDataRecieved, ExchangeDataRecieved> {
 
	void save(String i);
	public List<ExchangeDataRecieved> findAll();

}