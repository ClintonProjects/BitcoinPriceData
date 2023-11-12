package com.bitcoinprice.analytics.services;

import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.bitcoinprice.analytics.localmodel.UserIPInformation;
import com.bitcoinprice.analytics.repository.IpRepository;

@Service
@ComponentScan({ "com.bitcoinprice", "com.bitcoinprice.repository" })
public class IpService {

	@Autowired
	private IpRepository exampleRepository;
	
	@Autowired
	Auth auth;

	public void saveIp(Map<String, String> jsonJavaRootObject2) throws UnknownHostException {
		// Gets the IPS from the map.
		String ipv4 = jsonJavaRootObject2.getOrDefault("ipv4", "");
		String ipv6 = jsonJavaRootObject2.getOrDefault("ipv6", "");

		System.out.println("ipv4: " + ipv4);
		System.out.println("ipv6: " + ipv6);

		/*
		 * Some IPS can have ipv4 or ipv6 not present, so we have to take account all
		 * cases were they can be present or not which we do below
		 */

		if (!ipv4.isEmpty() && exampleRepository.findByipv4(ipv4) != null) {
			// System.out.println("IPV4 is presant in the database.");
			// if the IP is already in the database, then set increase the visits.
			UserIPInformation ipObject = exampleRepository.findByipv4(ipv4);
			ipObject.incrementVisit();
			exampleRepository.save(ipObject);
			return;
		} else if (!ipv6.isEmpty() && exampleRepository.findByipv6(ipv6) != null) {
			// System.out.println("ipv6 is presant in the database.");
			// if the IP is already in the database, then set increase the visits.
			UserIPInformation ipObject = exampleRepository.findByipv6(ipv6);
			ipObject.incrementVisit();
			exampleRepository.save(ipObject);
			return;
		} else {
			// System.out.println("ip is not in the database.");
			// if the IP is not in the database, then add it to the database.
			UserIPInformation temp = new UserIPInformation(new ObjectId(), ipv4, ipv6);
			exampleRepository.save(temp);
			return;
		}
	}

	public Map<String, Integer> getAllUniqueCountryVisted(String id) {
		if (auth.AuthUserBySessionId(id)) {
			// returns list of countries in order of visits (These are single views or
			// unique)
			List<String> addresses = exampleRepository.findAll().stream().map(UserIPInformation::getCountryFromLocation)
					.sorted().collect(Collectors.toList());
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String i : addresses)
				if (!map.containsKey(i))
					map.put(i, 1);
				else
					map.put(i, map.get(i) + 1);
			return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
							LinkedHashMap::new));
		} else
			return null;
	}

	public Map<String, Integer> getAllCountryVisted() {
		// returns list of countries in order of visits
		List<UserIPInformation> ipObject = exampleRepository.findAll();
		Map<String, Integer> map = new HashMap<String, Integer>();

		for (UserIPInformation i : ipObject)
			if (!map.containsKey(i.getCountryFromLocation()))
				map.put(i.getCountryFromLocation(), i.getNumberOfVisit());
			else
				map.put(i.getCountryFromLocation(), map.get(i.getCountryFromLocation()) + i.getNumberOfVisit());

		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors
				.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}

	public int getUniqueViewers() {
		// returns the amount of unique views, this may not be 100% accurate method,
		// because people can use VPNS etc
		List<UserIPInformation> ipObject = exampleRepository.findAll();
		return ipObject.size();
	}

	public int getTotalViewers() {
		// returns the total amount of user viewing the website.
		List<UserIPInformation> ipObject = exampleRepository.findAll();
		return ipObject.stream().mapToInt(i -> i.getNumberOfVisit()).sum();
	}
}
