package com.bitcoinprice.dataparsing.exchanges;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.bitcoinprice.dataparsing.coindata.ExchangeDataRecieved;
import com.bitcoinprice.dataparsing.requests.Requests;
import com.bitcoinprice.dataparsing.requests.JsonConverter;

public class ApiExchangeToData {

	protected JSONArray dataExample;
	protected ArrayList<ExchangeDataRecieved> exchangeDataList;

	public ApiExchangeToData() {
		Integer x = Integer.MAX_VALUE;
	}

	@Deprecated
	// This is only used for post requests, it's deprecated since it meant to be
	// avoided to be used.
	public ApiExchangeToData(JSONObject jsonData) throws JSONException, UnsupportedOperationException, IOException {
		String request = new Requests().getRequest(jsonData.getString("apiLink"));
		dataExample = new JsonConverter().stringToObjectArray(request);
		exchangeDataList = new ArrayList<ExchangeDataRecieved>();

		for (int i = 0; i < dataExample.length(); i++) {
			JSONObject firstResult = (JSONObject) dataExample.get(i);
			ExchangeDataRecieved exchangeDataRecieved = new ExchangeDataRecieved();

			exchangeDataRecieved.setApiLink(jsonData.getString("apiLink"));
			exchangeDataRecieved.setExchange(jsonData.getString("exchange"));
			exchangeDataRecieved.setCypto(jsonData.getString("cypto").toUpperCase());
			exchangeDataRecieved.setCurrency(jsonData.getString("currency").toUpperCase());

			if (firstResult.has(jsonData.getString("side")))
				exchangeDataRecieved.setSide(firstResult.getString(jsonData.getString("side")));

			exchangeDataRecieved.setTranactionId(firstResult.getString(jsonData.getString("tranactionId")));
			exchangeDataRecieved.setPrice(firstResult.getString(jsonData.getString("price")));
			exchangeDataRecieved.setSize(firstResult.getString(jsonData.getString("size")));
			exchangeDataRecieved.setSymbol(jsonData.getString("symbol"));
			exchangeDataRecieved.setTimestamp(firstResult.getString(jsonData.getString("timestamp")));

			exchangeDataList.add(exchangeDataRecieved);
		}
	}

	public void BitmexAndCoinbase(JSONArray getNotePadData, String apiData, String link, boolean JsonArrayRequired)
			throws JSONException, UnsupportedOperationException, IOException {

		// Json data from websocket.
		dataExample = new JsonConverter().stringToObjectArray("[" + apiData + "]");

		// exchange data list:
		exchangeDataList = new ArrayList<ExchangeDataRecieved>();

		for (int i = 0; i < getNotePadData.length(); i++) {
			// Note pad loop
			JSONObject dataConversationText = (JSONObject) getNotePadData.get(i);
			String apiLinkFromNotepad = String.valueOf(dataConversationText.get("apiLink"));

			if (!link.equalsIgnoreCase(apiLinkFromNotepad))
				continue;
			else {
				for (int j = 0; j < dataExample.length(); j++) {
					ExchangeDataRecieved exchangeDataRecieved = new ExchangeDataRecieved();

					boolean dataRequired = JsonArrayRequired;
					JSONArray listOfData;
					int loopSize = 0;

					if (dataRequired) {
						// This line may need to be removed for other exchanges:
						if (((JSONObject) dataExample.get(j)).has("data")) {
							String wekSocketDataToString = ((JSONObject) dataExample.get(j)).getString("data");
							// System.out.println(wekSocketDataToString);
							listOfData = new JSONArray(wekSocketDataToString);
							loopSize = listOfData.length();
							// System.out.println("loopSize: " + loopSize);
						} else {
							// for error catching
							return;
						}
					} else {
						// This line may need to be removed for other exchanges:
						JSONObject wekSocketDataToString = ((JSONObject) dataExample.get(j));
						// System.out.println(wekSocketDataToString);
						listOfData = new JSONArray("[" + wekSocketDataToString + "]");
						loopSize = listOfData.length();
					}

					for (int k = 0; k < loopSize; k++) {
						try {
							JSONObject firstResult = (JSONObject) listOfData.get(k);

							exchangeDataRecieved.setApiLink(dataConversationText.getString("apiLink"));
							exchangeDataRecieved.setExchange(dataConversationText.getString("exchange"));

							if (firstResult.has("product_id")) {
								exchangeDataRecieved.setCypto(firstResult.get("product_id").toString().split("-")[0]);
								exchangeDataRecieved
										.setCurrency(firstResult.get("product_id").toString().split("-")[1]);
								exchangeDataRecieved
										.setSide(firstResult.getString(dataConversationText.getString("side")));
								exchangeDataRecieved.setSymbol(firstResult.get("product_id").toString().split("-")[0]
										+ "/" + firstResult.get("product_id").toString().split("-")[1]);
							} else {
								exchangeDataRecieved.setCypto(dataConversationText.getString("cypto").toUpperCase());
								exchangeDataRecieved
										.setCurrency(dataConversationText.getString("currency").toUpperCase());
								exchangeDataRecieved.setSymbol(dataConversationText.getString("cypto").toUpperCase()
										+ "/" + dataConversationText.getString("currency").toUpperCase());
							}

							if (firstResult.has(dataConversationText.getString("side")))
								exchangeDataRecieved
										.setSide(firstResult.getString(dataConversationText.getString("side")));

							exchangeDataRecieved.setTranactionId("Removed bug");
							exchangeDataRecieved.setPrice(firstResult.getString(dataConversationText.getString("price")));
							exchangeDataRecieved.setSize(firstResult.getString(dataConversationText.getString("size")));

							if (!dataConversationText.getString("timestamp").equals("setbycode"))
								exchangeDataRecieved.setTimestamp(
										firstResult.getString(dataConversationText.getString("timestamp")));

							if (dataConversationText.getString("timestamp").equals("setbycode")) {
								Calendar calendar = Calendar.getInstance();
								Date time = calendar.getTime();
								exchangeDataRecieved.setTimestamp(time.toGMTString());
							}
							
							//the return, returns the updated currency
							exchangeDataRecieved = new Currency().convertExchangeData(exchangeDataRecieved);
							exchangeDataList.add(exchangeDataRecieved);

						} catch (Exception e) {
							System.out.println(e + dataConversationText.getString("apiLink"));
						}
					}
				}
			}
		}
	}

	public ExchangeDataRecieved storeBainaceData(String data, int dataPos) throws Exception {
//		System.out.println(data);

		try {
			// turns the data into easy way to handle it
			dataExample = new JsonConverter().stringToObjectArray("[" + data + "]");
			String wekSocketDataToString = ((JSONObject) dataExample.get(0)).toString();
			JSONArray listOfData = new JSONArray("[" + wekSocketDataToString + "]");
			JSONObject firstResult = (JSONObject) listOfData.get(0);

			ExchangeDataRecieved exchangeDataRecieved = new ExchangeDataRecieved();

			JSONArray getNotePadData = new AllDataList().getNotePadDataJsonArray();
			JSONObject dataConversationText = (JSONObject) getNotePadData.get(dataPos);

			// sets the data to be stored.
			exchangeDataRecieved.setApiLink(dataConversationText.getString("apiLink"));
			exchangeDataRecieved.setExchange(dataConversationText.getString("exchange"));
			exchangeDataRecieved.setCypto(dataConversationText.getString("cypto").toUpperCase());
			exchangeDataRecieved.setCurrency(dataConversationText.getString("currency").toUpperCase());
			exchangeDataRecieved.setPrice(firstResult.getString(dataConversationText.getString("price")));
			exchangeDataRecieved.setSymbol(dataConversationText.getString("symbol"));
			exchangeDataRecieved.setSize(firstResult.getString(dataConversationText.getString("size")));
			String side = firstResult.getString(dataConversationText.getString("side"));
			exchangeDataRecieved.setSide(side.equals("true") ? "buy" : "sell");
			Instant instant = Instant.now();
			exchangeDataRecieved.setTimestamp(instant.toString());

			//the return, returns the updated currency
			return  new Currency().convertExchangeData(exchangeDataRecieved);

		} catch (JSONException | IOException e) {
			// if an error caught end the method.
			e.printStackTrace();
		}

		throw new Exception("invaild value entered");
	}

//	{
//		  "e": "trade",     // Event type
//		  "E": 123456789,   // Event time
//		  "s": "BNBBTC",    // Symbol
//		  "t": 12345,       // Trade ID
//		  "p": "0.001",     // Price
//		  "q": "100",       // Quantity
//		  "b": 88,          // Buyer order ID
//		  "a": 50,          // Seller order ID
//		  "T": 123456785,   // Trade time
//		  "m": true,        // Is the buyer the market maker?
//		  "M": true         // Ignore
//		}

	public JSONArray getDataExample() {
		return dataExample;
	}

	public ArrayList<ExchangeDataRecieved> getExchangeDataList() {
		return exchangeDataList;
	}
}
