package com.bitcoinprice.dataparsing.coindata;

public class AveragePrices {

	public String exchangeName;
	public double avgValue;
	
	public AveragePrices(String exchangeName, double avgValue) {
		this.exchangeName = exchangeName;
		this.avgValue = avgValue;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public double getAvgValue() {
		return avgValue;
	}

	public void setAvgValue(double avgValue) {
		this.avgValue = avgValue;
	}
	
}
