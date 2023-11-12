package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bitcoinprice.dataparsing.coindata.ExchangeDataRecieved;
import com.bitcoinprice.dataparsing.enums.CurrenciesEnums;
import com.bitcoinprice.dataparsing.exchanges.Currency;
import com.example.repository.BitcoinPriceData;

@Service
@ComponentScan({ "com.example", "com.example.repository" })
public class CurrencyService {

	@Autowired
	private BitcoinPriceData bitcoinPriceData;
	
	@Scheduled(fixedRate = 1000 * 60 * 2)
	public void scheduledUpdate() throws Exception {
		//Updates the currency conversation holder.
		new Currency().getCurrencyValues();	
	}

	
}
