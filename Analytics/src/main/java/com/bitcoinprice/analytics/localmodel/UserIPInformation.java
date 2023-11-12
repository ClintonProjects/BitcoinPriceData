package com.bitcoinprice.analytics.localmodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "IpInformation")
@Entity
public class UserIPInformation {

	@Id
	private ObjectId _id;
	private String ipv4;
	private String ipv6;
	private int numberOfVisit = 1;
	private ArrayList<String> timesVisited = new ArrayList<String>();
	private LocationBasedIP location;

	public UserIPInformation(ObjectId _id, String ipv4, String ipv6) {
		super();
		this._id = _id;
		this.ipv4 = ipv4;
		this.ipv6 = ipv6;
		setTimesVisited();

		// Creates the location in the database.
		String uri = "http://ip-api.com/json/" + ipv4
				+ "?fields=status,message,continent,continentCode,country,countryCode,region,regionName,city,district,zip,lat,lon,timezone,offset,currency,isp,org,as,asname,reverse,mobile,proxy,hosting,query";
		HashMap<?, ?> e = new RestTemplate().getForObject(uri, HashMap.class);
		location = new LocationBasedIP(e.get("continent"), e.get("country"), e.get("city"), e.get("regionName"),
				e.get("timezone"), e.get("isp"), e.get("mobile"), e.get("proxy"), e.get("hosting")
				, e.get("lat"), e.get("lon"));
	}

	public String getCountryFromLocation() {
		return location.getCountry();
	}

	// Increases the number of times the users have visited.
	public int getNumberOfVisit() {
		return numberOfVisit;
	}

	// set the time when user has visited
	public void setTimesVisited() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		timesVisited.add(myDateObj.format(myFormatObj));
	}

	// Adds a time to the database since it's new viewer and increases the number of
	// IP when the user visit.
	public void incrementVisit() {
		numberOfVisit++;
		setTimesVisited();
	}
}
