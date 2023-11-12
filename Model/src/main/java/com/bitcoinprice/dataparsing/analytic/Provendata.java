package com.bitcoinprice.dataparsing.analytic;

import java.util.ArrayList;

import com.bitcoinprice.dataparsing.coindata.ExchangeDataRecieved;
import com.bitcoinprice.dataparsing.coindata.RealTimeBTCData;

public class Provendata {

	private String ticker = ""; //exchange/currency/cypto
	private double buyTotal;
	private double sellTotal;
	private double dataSize;
	private ArrayList<Double> priceChange;
	private int dataAccuracy;
	private int dataNotAccuracy;
	private  ArrayList<RealTimeBTCData> realTimeBTCData;
	
	public Provendata() {}
	
	public Provendata(String ticker, double buyTotal, double sellTotal, double dataSize, ArrayList<Double> priceChange,
			int dataAccuracy, int dataNotAccuracy, ArrayList<RealTimeBTCData> realTimeBTCData) {
		this.ticker = ticker;
		this.buyTotal = buyTotal;
		this.sellTotal = sellTotal;
		this.dataSize = dataSize;
		this.priceChange = priceChange;
		this.dataAccuracy = dataAccuracy;
		this.dataNotAccuracy = dataNotAccuracy;
		this.realTimeBTCData = realTimeBTCData;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public double getBuyTotal() {
		return buyTotal;
	}

	public void setBuyTotal(double buyTotal) {
		this.buyTotal = buyTotal;
	}

	public double getSellTotal() {
		return sellTotal;
	}

	public void setSellTotal(double sellTotal) {
		this.sellTotal = sellTotal;
	}

	public double getDataSize() {
		return dataSize;
	}

	public void setDataSize(double dataSize) {
		this.dataSize = dataSize;
	}
	
	

	public ArrayList<Double> getPriceChange() {
		return priceChange;
	}

	public void addPriceChange(double add) {
		priceChange.add(add);
	}

	public void setPriceChange(ArrayList<Double> priceChange) {
		this.priceChange = priceChange;
	}

	public int getDataAccuracy() {
		return dataAccuracy;
	}

	public void setDataAccuracy(int dataAccuracy) {
		this.dataAccuracy = dataAccuracy;
	}

	public int getDataNotAccuracy() {
		return dataNotAccuracy;
	}

	public void setDataNotAccuracy(int dataNotAccuracy) {
		this.dataNotAccuracy = dataNotAccuracy;
	}

	public ArrayList<RealTimeBTCData> getRealTimeBTCData() {
		return realTimeBTCData;
	}

	public void setRealTimeBTCData(ArrayList<RealTimeBTCData> realTimeBTCData) {
		this.realTimeBTCData = realTimeBTCData;
	}
	

	

}
