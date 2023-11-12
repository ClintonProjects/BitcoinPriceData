package com.bitcoinprice.analytics.controllors;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bitcoinprice.analytics.services.IpService;
import com.bitcoinprice.analytics.services.Auth;
import com.bitcoinprice.dataparsing.analytic.Feedback;
import com.bitcoinprice.dataparsing.user.Login;
import com.bitcoinprice.analytics.services.FeedbackService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/AnalyticsService")
@CrossOrigin(origins = "*")
public class IpContollors {

	@Autowired
	IpService IpService;

	@Autowired
	FeedbackService FeedbackService;

	@Autowired
	Auth auth;

	// http://localhost:8080/AnalyticsService/getTotalViews
	@GetMapping("/t")
	public String t() {
		return "test";
	}

	// http://localhost:8080/AnalyticsService/getTotalViews
	@GetMapping("/getTotalViews")
	public int getAll() {
		return IpService.getTotalViewers();
	}

	@GetMapping("/getAllUniqueCountryVisted/{id}")
	@ResponseBody
	public Map<String, Integer> getAllUniqueCountryVisted(@PathVariable String id) {
		return IpService.getAllUniqueCountryVisted(id);
	}

	@GetMapping("/getAllCountryVisted")
	public Map<String, Integer> getViewsFromCountry() {
		return IpService.getAllCountryVisted();
	}

	// http://localhost:8080/AnalyticsService/getUniqueViews
	@GetMapping("/getUniqueViews")
	public int getUniqueviews() {
		return IpService.getUniqueViewers();
	}

	// http://localhost:8080/AnalyticsService/UserVisitedTableStore
	@PostMapping("/AnalyticsData")
	public void saveTest(@RequestBody String ips) throws UnknownHostException {
		Map<String, String> jsonJavaRootObject = new Gson().fromJson(ips, Map.class);
		IpService.saveIp(jsonJavaRootObject);
	}

	@PostMapping(value = "/SaveFeedback")
	public void RegisterUser(@RequestBody Feedback feedback) throws Exception {
		FeedbackService.insertFeedback(feedback);
	}

	// s

	@GetMapping("/getFeedback/{id}/{index}")
	@ResponseBody
	public ArrayList<Feedback> getFooById(@PathVariable String id, @PathVariable int index) throws Exception {
		ArrayList<Feedback> feedback = new ArrayList<Feedback>();
		feedback.add(FeedbackService.getFeedback(id, index));
		return feedback;
	}
//
	@GetMapping("/getSatifcationRate/{id}")
	public HashMap<String, Object> getSatifcationRatethrows(@PathVariable String id) throws Exception {
		return FeedbackService.satifcationRate(id);
	}

	// This is random letters for security reasons.
	@GetMapping("/AnaylticsRedirect/{id}")
	public String getAnaylticsNav(@PathVariable String id) throws Exception {
		if (auth.AuthUserBySessionId(id))
			return "a";
		else
			return "http://localhost:3000/";
	}
}
