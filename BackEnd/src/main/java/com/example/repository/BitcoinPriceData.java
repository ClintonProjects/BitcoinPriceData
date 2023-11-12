package com.example.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bitcoinprice.dataparsing.coindata.ExchangeDataRecieved;
import com.example.model.ExampleObject;

@Repository
@Transactional
public interface BitcoinPriceData  extends MongoRepository<ExchangeDataRecieved, ExchangeDataRecieved> {
 
	void save(String i);
	public List<ExchangeDataRecieved> findAll();
	
//	  examples:
//	  public Sample findByFirstName(String firstName);
//	  public List<Sample> findByLastName(String lastName);
	
}