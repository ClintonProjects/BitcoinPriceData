package com.example.controllors;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bitcoinprice.dataparsing.coindata.RealTimeBTCData;
import com.bitcoinprice.dataparsing.coindata.ExchangeDataRecieved;
import com.example.services.ServicesExample;

@RestController
@RequestMapping("/apis")
@CrossOrigin(origins = "*")
public class EndPoints {

	@Autowired
	ServicesExample servicesExample;

	// http://localhost:8080/apis/btcprice
	@GetMapping("/btcprice")
	public RealTimeBTCData getAll() throws JSONException, IOException {
		return servicesExample.getRealTimeBTCData(null,null,null);
//		return null;
	}

	@GetMapping("/getTenList")
	public List<ExchangeDataRecieved> getExchngeData() throws JSONException, IOException {
		return servicesExample.getTenLatestTranactions();
	}


}
