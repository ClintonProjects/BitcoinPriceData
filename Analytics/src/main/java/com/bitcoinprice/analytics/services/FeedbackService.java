package com.bitcoinprice.analytics.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.bitcoinprice.analytics.repository.FeedbackRepository;
import com.bitcoinprice.dataparsing.analytic.Feedback;

@Service
@ComponentScan({ "com.bitcoinprice", "com.bitcoinprice.repository" })
public class FeedbackService {

	@Autowired
	private FeedbackRepository FeedbackRepository;

	@Autowired
	Auth auth;

	public void insertFeedback(Feedback feedback) throws Exception {
		if (feedback.getRating().isEmpty() || feedback.text.isEmpty() || feedback.text.length() > 200
				|| feedback.getType().isEmpty())
			return;
		// safety number;
		FeedbackRepository.insert(feedback);
	}

	public Feedback getFeedback(String id, int value) throws Exception {
		// This values will return the latest feedback.
		if (auth.AuthUserBySessionId(id)) {
			List<Feedback> feedback = FeedbackRepository.findAll();
			if (feedback.size() < value || value == 0)
				return new Feedback("", "", "");
			int feedbackLenght = feedback.size();
			return feedback.get(feedbackLenght - value);
		}
		return null;
	}

	public HashMap<String, Object> satifcationRate(String sessionId) {
		if (auth.AuthUserBySessionId(sessionId)) {
			HashMap<String, Object> satifcationRate = new HashMap<String, Object>();
			List<Feedback> feedback = FeedbackRepository.findAll();

			@SuppressWarnings("removal")
			double VeryUnhappyCount = new Double(
					(feedback.stream().filter(i -> i.getRating().equalsIgnoreCase("Very Unhappy")).count() * 100.0f)
							/ feedback.size());
			@SuppressWarnings("removal")
			double unHappyCount = new Double(
					(feedback.stream().filter(i -> i.getRating().equalsIgnoreCase("Unhappy")).count() * 100.0f)
							/ feedback.size());
			@SuppressWarnings("removal")
			double okCount = new Double(
					(feedback.stream().filter(i -> i.getRating().equalsIgnoreCase("Ok")).count() * 100.0f)
							/ feedback.size());
			@SuppressWarnings("removal")
			double goodCount = new Double(
					(feedback.stream().filter(i -> i.getRating().equalsIgnoreCase("Gppd")).count() * 100.0f)
							/ feedback.size());
			@SuppressWarnings("removal")
			double veryGoodCount = new Double(
					(feedback.stream().filter(i -> i.getRating().equalsIgnoreCase("Very Good")).count() * 100.0f)
							/ feedback.size());

			// Double((buyCount * 100.0f) / totalCount);
			satifcationRate.put("Very Unhappy", VeryUnhappyCount);
			satifcationRate.put("Unhappy", unHappyCount);
			satifcationRate.put("Ok", okCount);
			satifcationRate.put("Good", okCount);
			satifcationRate.put("Very Good", veryGoodCount);

			ArrayList<Double> list = new ArrayList<Double>();						
			list.add(VeryUnhappyCount);
			list.add(unHappyCount);
			list.add(okCount);
			list.add(goodCount);
			list.add(veryGoodCount);
			
			ArrayList<String> feedbackStuff = new ArrayList<String>();						
			feedbackStuff.add("Very Unhappy");
			feedbackStuff.add("Unhappy");
			feedbackStuff.add("Ok");
			feedbackStuff.add("Good");
			feedbackStuff.add("Very Good");
			
			double highestValue = list.stream().mapToDouble(v -> v).max().orElse(0);
			satifcationRate.put("MostCommon", feedbackStuff.get(list.indexOf(highestValue)));
			satifcationRate.entrySet().stream().forEach(e -> System.out.println("Map: " + e));

			return satifcationRate;
		} else
			return null;
	}

}
