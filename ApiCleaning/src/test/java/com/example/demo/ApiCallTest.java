package com.example.demo;

import java.io.IOException;

import org.junit.Test;

import com.bitcoinprice.dataparsing.requests.Requests;

import static org.junit.Assert.*;

class apiCallTest {

	public void apiTestMethod(String site, String conversation, String link)
			throws UnsupportedOperationException, IOException {
		System.out.println("Api Code test [" + site + "] " + conversation);
		String TestResult = new Requests().getRequest(link);
		assertTrue(TestResult != null && !TestResult.isEmpty());
	}

	@Test
	public void ApiTestBitmexBTCUSD() throws IOException {
		apiTestMethod("bitmex.com", "USD/BTC",
				"https://www.bitmex.com/api/v1/trade?count=200&reverse=true&symbol=XBTUSD");
	}
}