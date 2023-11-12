package com.bitcoinprice.dataparsing.exchangeprice;

import java.util.ArrayList;

public class ListOfExchangeDatas {

	private ArrayList<ExchangePrices> BTC = new ArrayList<ExchangePrices>();
	private ArrayList<ExchangePrices> LTC = new ArrayList<ExchangePrices>();
	private ArrayList<ExchangePrices> ETH = new ArrayList<ExchangePrices>();

	public ListOfExchangeDatas(ArrayList<ExchangePrices> bTC, ArrayList<ExchangePrices> lTC,
			ArrayList<ExchangePrices> eTH) {
		super();
		BTC = bTC;
		LTC = lTC;
		ETH = eTH;
	}

	public ArrayList<ExchangePrices> getBTC() {
		return BTC;
	}

	public void setBTC(ArrayList<ExchangePrices> bTC) {
		BTC = bTC;
	}

	public ArrayList<ExchangePrices> getLTC() {
		return LTC;
	}

	public void setLTC(ArrayList<ExchangePrices> lTC) {
		LTC = lTC;
	}

	public ArrayList<ExchangePrices> getETH() {
		return ETH;
	}

	public void setETH(ArrayList<ExchangePrices> eTH) {
		ETH = eTH;
	}

}
