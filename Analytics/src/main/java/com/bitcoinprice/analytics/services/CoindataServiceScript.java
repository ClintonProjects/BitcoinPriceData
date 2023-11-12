package com.bitcoinprice.analytics.services;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.bitcoinprice.analytics.repository.BardataRepository;
import com.bitcoinprice.analytics.repository.ProvendataRepository;
import com.bitcoinprice.analytics.repository.BitcoinPriceData;
import com.bitcoinprice.dataparsing.analytic.Bardata;
import com.bitcoinprice.dataparsing.analytic.Provendata;
import com.bitcoinprice.dataparsing.coindata.ExchangeDataRecieved;
import com.bitcoinprice.dataparsing.coindata.RealTimeBTCData;

@Service
@ComponentScan({ "com.bitcoinprice", "com.bitcoinprice.repository" })
public class CoindataServiceScript {

	@Autowired
	private BitcoinPriceData bitcoinPriceData;

	@Autowired
	private BardataRepository bardataRepository;

	@Autowired
	private ProvendataRepository provendataRepository;

	private final List<String> CYPTO = Arrays.asList("BTC", "LTC", "ETH");
	private final List<String> EXCHANGE = Arrays.asList("COINBASE PRO", "BITMEX", "BINANCE");
	private final List<String> CURRENCY = Arrays.asList("USD", "EUR", "GBP");

	// Stores data about the bar in database.
	public void buySellBar(String exchange, String currency, String cointype) throws Exception {
		// could just return here, but rather have the error message.
		if (currency == null || cointype == null || exchange == null)
			throw new Exception("needs all 3 exchanges to not be null to work");
		// filters the data so we only get the content we need.
		List<ExchangeDataRecieved> previousMinList = bitcoinPriceData.findAll().stream().distinct()
				.filter(i -> i.getCurrency().trim().equalsIgnoreCase(currency.trim()))
				.filter(i -> i.getCypto().trim().equalsIgnoreCase(cointype.trim()))
				.filter(i -> i.getExchange().trim().equalsIgnoreCase(exchange.trim()))
				.filter(i -> Duration.between(LocalDateTime.ofInstant(i.getTimestamp1(), ZoneOffset.UTC),
						LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)).toSeconds() <= 60)
				.collect(Collectors.toList());
		// Just to keep easier to read.
		long buyCount = previousMinList.stream().distinct().filter(i -> i.getSide().equalsIgnoreCase("Buy")).count();
		// Builder, just neater then 100 setters.

		RealTimeBTCData pricedata = averagePrice(exchange, currency, cointype);

		Bardata barData = new Bardata.Builder().exchange(exchange).currency(currency).cypto(cointype)
				.buyPercentage((buyCount * 100.0f) / previousMinList.size())
				.sellPercentage(100 - ((buyCount * 100.0f) / previousMinList.size())).realTimeBTCData(pricedata)
				.dataSize(previousMinList.size()).build();

		barData.addExchangeDataRecieved(new ArrayList<ExchangeDataRecieved>(previousMinList));

		// doesn't save if we have a count of 0, this is because we sometimes have times
		// were low cap coins have no good data. example ltc
		if (barData.getDataSize() > 0)
			bardataRepository.save(barData);
	}

	public RealTimeBTCData averagePrice(String exchange, String currency, String cointype) throws Exception {
		if (currency == null || cointype == null || exchange == null)
			throw new Exception("needs all 3 exchanges to not be null to work");
		List<ExchangeDataRecieved> db = bitcoinPriceData.findAll();

		List<ExchangeDataRecieved> previousMinList = db.stream().distinct()
				.filter(i -> i.getCurrency().trim().equalsIgnoreCase(currency.trim()))
				.filter(i -> i.getCypto().trim().equalsIgnoreCase(cointype.trim()))
				.filter(i -> i.getExchange().trim().equalsIgnoreCase(exchange.trim()))
				.filter(i -> Duration.between(LocalDateTime.ofInstant(i.getTimestamp1(), ZoneOffset.UTC),
						LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)).toSeconds() <= 60)
				.collect(Collectors.toList());

		if (previousMinList.size() == 0)
			return null;

		List<ExchangeDataRecieved> afterMinList = db.stream().distinct()
				.filter(i -> i.getCurrency().trim().equalsIgnoreCase(currency.trim()))
				.filter(i -> i.getCypto().trim().equalsIgnoreCase(cointype.trim()))
				.filter(i -> i.getExchange().trim().equalsIgnoreCase(exchange.trim()))
				.filter(i -> Duration
						.between(LocalDateTime.ofInstant(i.getTimestamp1(), ZoneOffset.UTC),
								LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
						.toSeconds() >= 60
						&& Duration.between(LocalDateTime.ofInstant(i.getTimestamp1(), ZoneOffset.UTC),
								LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)).toSeconds() <= 120)
				.collect(Collectors.toList());

		if (afterMinList.size() == 0)
			return null;

		double cuurentMinPrice = previousMinList.stream().mapToDouble(i -> Double.parseDouble(i.getPrice())).sum()
				/ previousMinList.size();
		double twoMinAgoPrice = afterMinList.stream().mapToDouble(i -> Double.parseDouble(i.getPrice())).sum()
				/ afterMinList.size();

		RealTimeBTCData realTimeBTCData = new RealTimeBTCData(cuurentMinPrice, twoMinAgoPrice,
				previousMinList.stream().filter(i -> i.getSide().equalsIgnoreCase("Buy")).count(),
				previousMinList.stream().filter(i -> i.getSide().equalsIgnoreCase("sell")).count());

		return realTimeBTCData;
	}

	@Scheduled(fixedRate = 1000 * 60)
	public void scheduledUpdate() throws Exception {
		// This data is used for analytics part of project it's more like script then
		// anything else.
		// Updates the currency conversation holder.
		for (String i : EXCHANGE)
			for (String j : CURRENCY)
				for (String k : CYPTO)
					buySellBar(i, j, k);
	}

//	@Bean
	public void provenData() {
		// This code doesn't need to be neat, and might be mess, only needs to be used
		// once for script to prove
		// the concept so I am doing it in fastest way possible rather then neat code
		List<Bardata> allBardata = bardataRepository.findAll();
		HashMap<String, List<Bardata>> data = new HashMap<String, List<Bardata>>();
		int inc = 0;

		// Sort all data into hashmap.
		for (Bardata i : allBardata) {

			String ticker = i.getExchange() + "/" + i.getCurrency() + "/" + i.getCypto();

			if (!data.containsKey(ticker)) {
				System.out.println(++inc);
				// Add the key to the hashmap and the items (no values)
				List<Bardata> bardata = new ArrayList<Bardata>();
				bardata.add(i);
				data.put(ticker, bardata);
				continue;
			}

			// add keys and values to the map if they already exist
			List<Bardata> dataCurrentlyInMap = data.get(ticker);
			dataCurrentlyInMap.add(i);
			data.put(ticker, dataCurrentlyInMap);
		}

		// after writing this code it makes not sense why I didn't just go through
		// database and get that way,
		// would normally rewrite this but as I said it only going to be ran once.
		// (talking about code above not below)
		List<Provendata> provendataList = new ArrayList<Provendata>();
		for (Map.Entry<String, List<Bardata>> entry : data.entrySet()) {

			Provendata provendata = new Provendata();
			provendata.setTicker(entry.getKey());

			List<Bardata> exchangeData = entry.getValue().stream().filter(i -> i.getRealTimeBTCData() != null)
					.collect(Collectors.toList());

			// add total buys, sell and total to array.
			int totalBuys = exchangeData.stream().mapToInt(i -> (int) i.getRealTimeBTCData().getTotalBuys()).sum();
			int totalSells = exchangeData.stream().mapToInt(i -> (int) i.getRealTimeBTCData().getTotalSells()).sum();
			provendata.setBuyTotal(totalBuys);
			provendata.setSellTotal(totalSells);
			provendata.setDataSize(totalBuys + totalSells);

			List<RealTimeBTCData> barExchangeData = new ArrayList<RealTimeBTCData>();

			for (Bardata i : exchangeData) {
				barExchangeData.add(i.getRealTimeBTCData());
			}

			if (provendata.getRealTimeBTCData() == null)
				provendata.setRealTimeBTCData(new ArrayList<RealTimeBTCData>());

			ArrayList<RealTimeBTCData> realTimeBTCData = provendata.getRealTimeBTCData();
			realTimeBTCData.addAll(barExchangeData);
			provendata.setRealTimeBTCData(realTimeBTCData);

			provendataList.add(provendata);
		}

		for (Provendata i : provendataList) {
			if (i.getRealTimeBTCData() != null)
				for (RealTimeBTCData j : i.getRealTimeBTCData()) {
					if (j.getCurrentPrice() > j.getPreviousPrice()) {
						if (j.getPriceChange() > 0) {
							i.setDataAccuracy(i.getDataAccuracy() + 1);
						} else {
							i.setDataNotAccuracy(i.getDataNotAccuracy() + 1);
						}
					} else {
						if (j.getPriceChange() < 0) {
							i.setDataAccuracy(i.getDataAccuracy() + 1);
						} else {
							i.setDataNotAccuracy(i.getDataNotAccuracy() + 1);
						}
					}
				}
			provendataRepository.save(i);
		}
	}
}