package com.bitcoinprice.dataparsing.enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum CurrenciesEnums {

	USD("USD"), EUR("EUR"), GBP("GBP");

	String key;

	CurrenciesEnums(String key) {
		this.key = key;
	}

	// default constructor, used only for the OTHER case,
	// because OTHER doesn't need a key to be associated with.
	CurrenciesEnums() {
	}

	public static CurrenciesEnums getValue(String currencies) {
		if (currencies.toUpperCase().equalsIgnoreCase("USD"))
			return USD;
		if (currencies.toUpperCase().equalsIgnoreCase("EUR"))
			return EUR;
		if (currencies.toUpperCase().equalsIgnoreCase("GBP"))
			return GBP;
		throw new IllegalArgumentException();
	}

	public static String getValue(CurrenciesEnums currencies) {
		if (currencies.equals(USD))
			return "USD";
		if (currencies.equals(EUR))
			return "EUR";
		if (currencies.equals(GBP))
			return "GBP";
		throw new IllegalArgumentException();
	}

	public static ArrayList<String> allCurrencies() {
		return (ArrayList<String>) Arrays.asList("USD", "EUR", "GBP");
	}

}
