package com.bitcoinprice.dataparsing.exchanges;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bitcoinprice.dataparsing.coindata.ExchangeDataRecieved;
import com.bitcoinprice.dataparsing.enums.CurrenciesEnums;
import com.bitcoinprice.dataparsing.requests.GetRequest;
import com.bitcoinprice.dataparsing.requests.JsonConverter;
import com.bitcoinprice.dataparsing.requests.NotePad;

public class Currency {

	private String USDCurrency;
	private String EURCurrency;
	private String GBPCurrency;

	public void getCurrencyValues() throws Exception {
		// Sends request to get Data (This can't be obtained by one link sadly)
		USDCurrency = new GetRequest().sendGET(
				"https://free.currconv.com/api/v7/convert?q=USD_EUR,USD_GBP&compact=ultra&apiKey=58cd1eb71b416a345a04");
		EURCurrency = new GetRequest().sendGET(
				"https://free.currconv.com/api/v7/convert?q=EUR_USD,EUR_GBP&compact=ultra&apiKey=58cd1eb71b416a345a04");
		GBPCurrency = new GetRequest().sendGET(
				"https://free.currconv.com/api/v7/convert?q=GBP_USD,GBP_EUR&compact=ultra&apiKey=58cd1eb71b416a345a04");
		String result = USDCurrency.subSequence(0, USDCurrency.length() - 1) + ","
				+ EURCurrency.subSequence(1, EURCurrency.length() - 1) + ","
				+ GBPCurrency.subSequence(1, GBPCurrency.length());

		// writeToFile
		new NotePad().writeData("currencies", "[" + result + "]");
	}

	public double convertCurrencies(CurrenciesEnums from, CurrenciesEnums to) throws Exception {
		// if there match return 1, since there same currency.
		if (from.equals(to))
			return 1;

		String currencyToFind = CurrenciesEnums.getValue(from) + "_" + CurrenciesEnums.getValue(to);
		String currency = new NotePad().read("currencies");
		JSONArray currencyJson = new JsonConverter().stringToObjectArray(currency);
		JSONObject firstResult = (JSONObject) currencyJson.get(0);
		if (firstResult.has(currencyToFind))
			return (double) firstResult.get(currencyToFind);
		throw new Exception("Currency not stored");
	}

	public ExchangeDataRecieved convertExchangeData(ExchangeDataRecieved exchangeDataRecieved) throws Exception {
		// convert the currencies
		double usdMul = convertCurrencies(CurrenciesEnums.valueOf(exchangeDataRecieved.getCurrency()),
				CurrenciesEnums.USD);
		double eurMul = convertCurrencies(CurrenciesEnums.valueOf(exchangeDataRecieved.getCurrency()),
				CurrenciesEnums.EUR);
		double gbpMul = convertCurrencies(CurrenciesEnums.valueOf(exchangeDataRecieved.getCurrency()),
				CurrenciesEnums.GBP);
		double currentPrice = Double.valueOf(exchangeDataRecieved.getPrice());

		// rounding down to 2 dec places
		DecimalFormat df2 = new DecimalFormat("#.00");
		exchangeDataRecieved.setPriceInUSD("$" + String.valueOf(df2.format(currentPrice * usdMul)));
		exchangeDataRecieved.setPriceInEUR("€" + String.valueOf(df2.format(currentPrice * eurMul)));
		exchangeDataRecieved.setPriceInGBP("£" + String.valueOf(df2.format(currentPrice * gbpMul)));
		return exchangeDataRecieved;
	}

}
