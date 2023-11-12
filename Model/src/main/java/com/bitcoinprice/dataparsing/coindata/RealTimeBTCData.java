package com.bitcoinprice.dataparsing.coindata;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RealTimeBTCData {
	
	public double currentPrice;
	public double previousPrice;
	public double priceChange;
	public long totalBuys;
	public long totalSells;
	
	
	
	public RealTimeBTCData() {
		super();
	}
	
	public RealTimeBTCData(double currentPrice, double previousPrice) {
		super();
		this.currentPrice  = currentPrice;
		this.previousPrice  = previousPrice;
		setRealTimePriceChange();
	}
	
	
	public RealTimeBTCData(double currentPrice, double previousPrice, long totalBuys, long totalSells) {
		this(currentPrice, previousPrice);
		this.totalBuys = totalBuys;
		this.totalSells = totalSells;
	}
	
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public double getPreviousPrice() {
		return previousPrice;
	}
	
	public void setRealTimePriceChange() {
		this.priceChange = (currentPrice * 100 / previousPrice) - 100;
	}
	
	public void setPreviousPrice(double previousPrice) {
		this.previousPrice = previousPrice;
	}
	public double getPriceChange() {
//		return 1;
		return (currentPrice * 100 / previousPrice) - 100;
	}

	public long getTotalBuys() {
		return totalBuys;
	}

	public void setTotalBuys(long totalBuys) {
		this.totalBuys = totalBuys;
	}

	public long getTotalSells() {
		return totalSells;
	}

	public void setTotalSells(long totalSells) {
		this.totalSells = totalSells;
	}

	public void setPriceChange(double priceChange) {
		this.priceChange = priceChange;
	}
	
	
}
