package com.example.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.binance.connector.client.impl.WebsocketClientImpl;
import com.bitcoinprice.dataparsing.exchanges.ApiExchangeToData;
import com.example.repository.BitcoinPriceData;

@Service
@ComponentScan({ "com.example", "com.example.repository" })
public class BainaceWebsocket {

	@Autowired
	private BitcoinPriceData bitcoinPriceData;
	
	@Async
	public void getData(int notepadPost, /* this is poistion of static int data in file */
			String symbol) throws IOException {
		new WebsocketClientImpl().tradeStream(symbol, ((event) -> {
			try {
				bitcoinPriceData.save(new ApiExchangeToData().storeBainaceData(event, notepadPost));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			client.closeAllConnections();
		}));
	}

}
