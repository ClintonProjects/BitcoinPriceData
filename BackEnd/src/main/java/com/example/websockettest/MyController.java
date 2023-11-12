package com.example.websockettest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.ServicesExample;

@RestController
public class MyController {

	@MessageMapping("/hello")
	@SendTo("/endpoint/greeting")
	public String getCurrentLocation() {
		System.out.println("connected");
		return "Connected confirmed from server side";
	}

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private ServicesExample servicesExample;

	@Async
	@Scheduled(fixedRate = 50)
	public void scheduledUpdate() {
//		System.out.println("scheduled");
		servicesExample.checkForNewEntries();
		this.template.convertAndSend("/endpoint/greeting", ServicesExample.previousExchangeDataRecieved);
	}
	
	@Async
	@Scheduled(fixedRate = 50)
	public void BuyandSellBarPercentage() {
		//This is for live buy and sell bar
//		System.out.println(servicesExample.buySellBar());
		this.template.convertAndSend("/endpoint/wow", servicesExample.buySellBar());
	}
	
	public void exchangesActivePrice() {
		//I have no idea what this does.
		this.template.convertAndSend("/endpoint/coinbaseprice", servicesExample.buySellBar());
	}
	
	@Async
	@Scheduled(fixedRate = 50)
	public void getExchangeData() {
		//Send back all the exchange data.
		this.template.convertAndSend("/endpoint/getExchangeData", servicesExample.averagePrice());
	}
	
	
	
	
}
	
	
	
	
	
	
	
