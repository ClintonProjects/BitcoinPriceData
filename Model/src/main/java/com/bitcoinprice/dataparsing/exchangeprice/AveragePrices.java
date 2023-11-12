package com.bitcoinprice.dataparsing.exchangeprice;

public class AveragePrices {
	
	private String Cypto;
	private String Currency;
	private double USDprice;
	private double GBPprice;
	private double EURprice;
	
	public AveragePrices(String cypto, String currency, double uSDprice, double gBPprice, double eURprice) {
		super();
		Cypto = cypto;
		Currency = currency;
		USDprice = uSDprice;
		GBPprice = gBPprice;
		EURprice = eURprice;
	}

	public String getCypto() {
		return Cypto;
	}

	public void setCypto(String cypto) {
		Cypto = cypto;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public double getUSDprice() {
		return USDprice;
	}

	public void setUSDprice(double uSDprice) {
		USDprice = uSDprice;
	}

	public double getGBPprice() {
		return GBPprice;
	}

	public void setGBPprice(double gBPprice) {
		GBPprice = gBPprice;
	}

	public double getEURprice() {
		return EURprice;
	}

	public void setEURprice(double eURprice) {
		EURprice = eURprice;
	}
}
