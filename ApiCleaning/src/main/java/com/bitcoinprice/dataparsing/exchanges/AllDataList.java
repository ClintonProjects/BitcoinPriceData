package com.bitcoinprice.dataparsing.exchanges;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bitcoinprice.dataparsing.requests.NotePad;

public class AllDataList {

	public ArrayList<ApiExchangeToData> dataList = new ArrayList<ApiExchangeToData>();

	public AllDataList() throws JSONException, IOException {}

	public void allApiData() throws UnsupportedOperationException, JSONException, IOException  {
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(new NotePad().read("WebsiteToScrapJson"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject JsonData = (JSONObject) jsonArray.get(i);
			ApiExchangeToData apiExchangeToData = new ApiExchangeToData(JsonData);
			dataList.add(apiExchangeToData);
		}
	}
	
	

	public JSONArray getNotePadDataJsonArray()  {
		try {
			return new JSONArray(new NotePad().read("WebsiteToScrapJson"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	
	
	public ArrayList<ApiExchangeToData> getDataList() {
		return dataList;
	}
	
}
