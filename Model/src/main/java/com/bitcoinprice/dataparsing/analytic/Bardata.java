package com.bitcoinprice.dataparsing.analytic;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.bitcoinprice.dataparsing.coindata.RealTimeBTCData;
import com.bitcoinprice.dataparsing.coindata.*;

public class Bardata {

	private String exchange = "";
	private String currency = "";
	private String cypto = "";
	private double buyPercentage;
	private double sellPercentage;
	private ArrayList<ExchangeDataRecieved> exchangeDataRecieved;
	private RealTimeBTCData realTimeBTCData;
	public String timestamp = "";
	private long dataSize;
	private double priceChange;
	
	private Bardata() {
	}

	private Bardata(Builder builder) {
		this.exchange = builder.exchange;
		this.currency = builder.currency;
		this.cypto = builder.cypto;
		this.buyPercentage = builder.buyPercentage;
		this.sellPercentage = builder.sellPercentage;
		this.realTimeBTCData = builder.realTimeBTCData;
		this.timestamp = Instant.now().toString();
		this.dataSize = builder.dataSize;
		this.priceChange = builder.priceChange;
	}

	public String getExchange() {
		return exchange;
	}

	public String getCurrency() {
		return currency;
	}

	public String getCypto() {
		return cypto;
	}

	public double getBuyPercentage() {
		return buyPercentage;
	}

	public double getSellPercentage() {
		return sellPercentage;
	}

	public void addExchangeDataRecieved(ArrayList<ExchangeDataRecieved> exchangeDataRecieved) {
		this.exchangeDataRecieved = exchangeDataRecieved;
	}

	public List<ExchangeDataRecieved> getExchangeDataRecieved() {
		return exchangeDataRecieved;
	}

	public RealTimeBTCData getRealTimeBTCData() {
		return realTimeBTCData;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public long getDataSize() {
		return dataSize;
	}
	
	public double getPriceChange() {
		return priceChange;
	}


	public static class Builder {
		private String exchange = "";
		private String currency = "";
		private String cypto = "";
		private double buyPercentage;
		private double sellPercentage;
		private RealTimeBTCData realTimeBTCData;
		private long dataSize;
		private double priceChange;

		public Builder exchange(final String exchange) {
			this.exchange = exchange;
			return this;
		}

		public Builder currency(final String currency) {
			this.currency = currency;
			return this;
		}

		public Builder cypto(final String cypto) {
			this.cypto = cypto;
			return this;
		}

		public Builder buyPercentage(final double buyPercentage) {
			this.buyPercentage = buyPercentage;
			return this;
		}

		public Builder sellPercentage(final double sellPercentage) {
			this.sellPercentage = sellPercentage;
			return this;
		}

		public Builder priceChange(final double priceChange) {
			this.priceChange = priceChange;
			return this;
		}

		public Builder realTimeBTCData(RealTimeBTCData realTimeBTCData) {
			this.realTimeBTCData = realTimeBTCData;
			return this;
		}

		public Builder dataSize(long dataSize) {
			this.dataSize = dataSize;
			return this;
		}

		public Bardata build() {
			return new Bardata(this);
		}
	}
}
