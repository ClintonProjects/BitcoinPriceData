package com.example.services;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bitcoinprice.dataparsing.coindata.ExchangeDataRecieved;
import com.bitcoinprice.dataparsing.coindata.RealTimeBTCData;
import com.bitcoinprice.dataparsing.exchanges.AllDataList;
import com.bitcoinprice.dataparsing.exchanges.ApiExchangeToData;
import com.example.repository.BitcoinPriceData;
import com.neovisionaries.ws.client.WebSocketException;

@Service
@ComponentScan({ "com.example", "com.example.repository" })
public class ServicesExample {

	@Autowired
	private BitcoinPriceData bitcoinPriceData;

	@Autowired
	CustomWebSocket customWebSocket;

	@Autowired
	BainaceWebsocket bainaceWebsocket;

	@Deprecated
	public void addExchangesToDB() throws JSONException, IOException {
		ArrayList<ApiExchangeToData> getDataList = new AllDataList().getDataList();
		List<String> adresses = bitcoinPriceData.findAll().stream().map(ExchangeDataRecieved::getTranactionId)
				.collect(Collectors.toList());

		for (ApiExchangeToData i : getDataList) {
			for (ExchangeDataRecieved j : i.getExchangeDataList()) {
				if (!adresses.contains(j.getTranactionId())) {
					bitcoinPriceData.save(j);
				}
			}
		}
	}

	@Bean
	public void addWebSocketDataToDB() throws JSONException, IOException, WebSocketException {
		customWebSocket.getWebSocket("wss://ws-feed.pro.coinbase.com/", false,
				"{\"type\": \"subscribe\", \"channels\":  [{\"name\":\"matches\",\"product_ids\":[\"BTC-USD\", \"BTC-GBP\" ,\"BTC-EUR\", \"ETH-GBP\" , \"ETH-USD\" , \"ETH-EUR\" , \"LTC-USD\" ,  \"LTC-GBP\" , \"LTC-EUR\"]}]}");
		customWebSocket.getWebSocket("wss://ws.bitmex.com/realtime?subscribe=trade:XBTUSD", true, "");
		customWebSocket.getWebSocket("wss://ws.bitmex.com/realtime?subscribe=trade:XBTEUR", true, "");
		customWebSocket.getWebSocket("wss://ws.bitmex.com/realtime?subscribe=trade:ETHUSD", true, "");
		customWebSocket.getWebSocket("wss://ws.bitmex.com/realtime?subscribe=trade:LTCUSD", true, "");
		bainaceWebsocket.getData(6, "btcusdt");
		bainaceWebsocket.getData(7, "ethusdt");
		bainaceWebsocket.getData(8, "ltcusdt");
		bainaceWebsocket.getData(9, "btceur");
		bainaceWebsocket.getData(10, "etheur");
		bainaceWebsocket.getData(11, "ltceur");
		bainaceWebsocket.getData(12, "btcgbp");
		bainaceWebsocket.getData(13, "ltcgbp");
		bainaceWebsocket.getData(14, "ethgbp");
	}

	public Double buySellBar() {
		List<ExchangeDataRecieved> currentDB = bitcoinPriceData.findAll().stream().collect(Collectors.toList());
		List<ExchangeDataRecieved> previousMinList = new ArrayList<ExchangeDataRecieved>();

		LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
		LocalDateTime minAgo = now.minus(1, ChronoUnit.MINUTES);

		for (ExchangeDataRecieved i : currentDB) {
			Instant instant = Instant.parse(i.getTimestamp());
			LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

			if (Duration.between(localDateTime, minAgo).getSeconds() <= 60)
				previousMinList.add(i);
		}

		long buyCount = previousMinList.stream().filter(i -> i.getSide().equalsIgnoreCase("Buy")).count();
		long totalCount = previousMinList.size();

		return new Double((buyCount * 100.0f) / totalCount);
	}

//	public List<ExchangeDataRecieved> getAllTranactions() {
//		List<ExchangeDataRecieved> currentDB = bitcoinPriceData.findAll();
//		currentDB.sort(Comparator.comparing(ExchangeDataRecieved::getTimestamp1).reversed());
//		return currentDB;
//	}

	@Deprecated
	public List<ExchangeDataRecieved> getTenLatestTranactions() {
		/*
		 * This code return latest exchange data but I implmented an improved version of
		 * it, leaving it in for protentail marks in my degree. (New method
		 * getDataForDataSocket();
		 */

		List<ExchangeDataRecieved> currentDB = bitcoinPriceData.findAll();

		// lets pretend this really bad code doesn't exist xD
		List<ExchangeDataRecieved> newList = new ArrayList<ExchangeDataRecieved>();

		List<String> currencies = currentDB.stream().map(ExchangeDataRecieved::getCurrency)
				.collect(Collectors.toList());
		List<String> exchange = currentDB.stream().map(ExchangeDataRecieved::getExchange).collect(Collectors.toList());
		List<String> cypto = currentDB.stream().map(ExchangeDataRecieved::getCypto).collect(Collectors.toList());

		for (int i = 0; i < currentDB.size(); i++) {
			boolean itemAvaiable = true;
			for (ExchangeDataRecieved j : newList) {
				if (currencies.get(i).trim().equalsIgnoreCase(j.getCurrency().trim())
						&& exchange.get(i).trim().equalsIgnoreCase(j.getExchange().trim())
						&& cypto.get(i).trim().equalsIgnoreCase(j.getCypto().trim())) {
					itemAvaiable = false;
				}
			}
			if (itemAvaiable)
				newList.add(currentDB.get(i));
		}

		List<ExchangeDataRecieved> resultList = new ArrayList<ExchangeDataRecieved>();

		currentDB.sort(Comparator.comparing(ExchangeDataRecieved::getTimestamp1).reversed());

		for (ExchangeDataRecieved i : newList) {
			resultList.addAll(currentDB.stream()
					.filter(j -> j.getCurrency().trim().equalsIgnoreCase(i.getCurrency().trim())
							&& j.getExchange().trim().equalsIgnoreCase(i.getExchange().trim())
							&& j.getCypto().trim().equalsIgnoreCase(i.getCypto().trim()))
					.limit(100).collect(Collectors.toList()));
		}

		resultList.sort(Comparator.comparing(ExchangeDataRecieved::getTimestamp1).reversed());

		// resultList.forEach(i -> System.out.println(i.getTimestamp()));

		// System.out.println("resultList size: " + resultList.get(0).getTimestamp1());

		return resultList;
	}

	// this list is are previous exchange data from the last cycle.
	public static List<ExchangeDataRecieved> previousExchangeDataRecieved;
	// checks if list has changed.
	public static boolean matchingData = true;

	// This method is used mainly for the websocket, it's basically just verifyes
	// that previous list is not the same as newest list.
	public void checkForNewEntries() {
		matchingData = true;

		// set the list incase it's the first run, stop bugs in other parts of the code.
		if (previousExchangeDataRecieved == null) {
			previousExchangeDataRecieved = new ArrayList<ExchangeDataRecieved>();
			return;
		}

		// This list contains are current exchange data.
		List<ExchangeDataRecieved> exchangeDataRecieved = getDataForDataSocket();

		if (previousExchangeDataRecieved.isEmpty()) {
			previousExchangeDataRecieved = exchangeDataRecieved;
			return;
		}

		if (exchangeDataRecieved.size() != previousExchangeDataRecieved.size()) {
			previousExchangeDataRecieved = exchangeDataRecieved;
			return;
		}

		// check if the 2 lists have all the same elemtns
		for (int i = 0; i < previousExchangeDataRecieved.size(); i++) {
			if (!exchangeDataRecieved.get(i).getTranactionId()
					.equals(previousExchangeDataRecieved.get(i).getTranactionId())) {
				previousExchangeDataRecieved = exchangeDataRecieved;
				return;
			}
		}
	}

	public HashMap<String, Object> averagePrice() {
		// Id data / result
		HashMap<String, Object> exchangeData = new HashMap<String, Object>();
		final List<String> EXCHANGE = Arrays.asList("ALL", "COINBASE PRO".trim(), "BITMEX", "BINANCE");
		for (String i : EXCHANGE)
			for (String j : CYPTO)
				for (String k : CURRENCY)
					exchangeData.put(i.toUpperCase() + "/" + j.toUpperCase() + "/" + k.toUpperCase(),
							getRealTimeBTCData(j.toUpperCase(), k.toUpperCase(), i.toUpperCase()));
		return exchangeData;
	}

	private final List<String> CYPTO = Arrays.asList("BTC", "LTC", "ETH");
	private final List<String> EXCHANGE = Arrays.asList("COINBASE PRO", "BITMEX", "BINANCE");
	private final List<String> CURRENCY = Arrays.asList("USD", "EUR", "GBP");
	private final List<Double> SIZE = Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0, 2.0, 5.0, Double.MAX_VALUE);
	public List<ObjectId> returnListObjList = new ArrayList<ObjectId>();

	public List<ExchangeDataRecieved> getDataForDataSocket() {
		List<ExchangeDataRecieved> returnList = new ArrayList<ExchangeDataRecieved>();
		List<ExchangeDataRecieved> all = bitcoinPriceData.findAll();
		int inc = -1;

		for (String i : CYPTO) {
			final int temp = ++inc;
			for (String j : EXCHANGE)
				for (String k : CURRENCY)
					for (Double l : SIZE) {
						if (l == 0.0)
							continue;
						returnList.addAll(all.stream().filter(m -> !returnListObjList.contains(m.get_id()))
								.filter(m -> m.getCypto().trim().equalsIgnoreCase(i.trim()))
								.filter(m -> m.getExchange().trim().equalsIgnoreCase(j.trim()))
								.filter(m -> m.getCurrency().trim().equalsIgnoreCase(k.trim()))
								.filter(m -> Double.valueOf(m.size) < l)
								.filter(m -> Double.valueOf(m.size) >= SIZE.get(temp))
								.sorted(Comparator.comparing(ExchangeDataRecieved::getTimestamp1).reversed()).limit(100)
								.collect(Collectors.toList()));
						returnListObjList = returnList.stream().map(ExchangeDataRecieved::get_id)
								.collect(Collectors.toList());
					}
		}

		returnList.sort(Comparator.comparing(ExchangeDataRecieved::getTimestamp1).reversed());

		return returnList;
	}

	// This is deprecated because it will replaced with another method or rewrite of
	// this method in an comming update.
//	@Deprecated
	public RealTimeBTCData getRealTimeBTCData(String cypto, String currency, String exchange) {
		List<ExchangeDataRecieved> currentDB = bitcoinPriceData.findAll().stream().collect(Collectors.toList());

		if (!exchange.equalsIgnoreCase("all"))
			currentDB = currentDB.stream().filter(i -> cypto != null && i.getCypto().equalsIgnoreCase(cypto))
					.filter(i -> currency != null && i.getCurrency().equalsIgnoreCase(currency))
					.filter(i -> i.getCurrency().equalsIgnoreCase(currency.trim()))
					.filter(i -> exchange != null && i.getExchange().trim().equalsIgnoreCase(exchange))
					.collect(Collectors.toList());
		else
			currentDB = currentDB.stream().filter(i -> cypto != null && i.getCypto().equalsIgnoreCase(cypto))
					.filter(i -> currency != null && i.getCurrency().equalsIgnoreCase(currency))
					.filter(i -> i.getCurrency().equalsIgnoreCase(currency)).collect(Collectors.toList());

		// Checks for tranactions that happened less then 1 min ago
		List<ExchangeDataRecieved> previousMinList = currentDB.stream()
				.filter(i -> Duration
						.between(LocalDateTime.ofInstant(Instant.parse(i.getTimestamp()), ZoneOffset.UTC),
								LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minus(1, ChronoUnit.MINUTES))
						.getSeconds() <= 60)
				.collect(Collectors.toList());

		// Checks for tranaction's that happened less then 60 seconds and 120 seconds
		// ago
		List<ExchangeDataRecieved> afterMinList = currentDB.stream()
				.filter(i -> Duration
						.between(LocalDateTime.ofInstant(Instant.parse(i.getTimestamp()), ZoneOffset.UTC),
								LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minus(1, ChronoUnit.MINUTES))
						.getSeconds() >= 60)
				.filter(i -> Duration
						.between(LocalDateTime.ofInstant(Instant.parse(i.getTimestamp()), ZoneOffset.UTC),
								LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minus(1, ChronoUnit.MINUTES))
						.getSeconds() <= 240)
				.collect(Collectors.toList());

		double cuurentMinPrice = previousMinList.stream().mapToDouble(i -> Double.parseDouble(i.getPrice())).sum()
				/ previousMinList.size();
		double twoMinAgoPrice = afterMinList.stream().mapToDouble(i -> Double.parseDouble(i.getPrice())).sum()
				/ afterMinList.size();

		RealTimeBTCData realTimeBTCData = new RealTimeBTCData(cuurentMinPrice, twoMinAgoPrice);

		return realTimeBTCData;
	}

	@Async
	@Scheduled(fixedRate = 1000 * 30)
	public void dropData() {
		try {
			LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
			List<ExchangeDataRecieved> resultToClear = bitcoinPriceData.findAll().stream().filter(i -> Duration
					.between(LocalDateTime.ofInstant(i.getTimestamp1(), ZoneOffset.UTC), now).getSeconds() > 240)
					.collect(Collectors.toList());
			bitcoinPriceData.deleteAll(resultToClear);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// only use in live demo version
//	@Async
//	@Scheduled(fixedRate = 1000 * 2400)
//	public void scheduledUpdate() throws Exception {
//		bitcoinPriceData.deleteAll();
//	}
}
