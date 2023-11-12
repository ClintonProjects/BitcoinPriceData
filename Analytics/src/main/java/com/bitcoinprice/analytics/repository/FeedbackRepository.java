package com.bitcoinprice.analytics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bitcoinprice.dataparsing.analytic.Bardata;
import com.bitcoinprice.dataparsing.analytic.Feedback;

@Repository
@Transactional
public interface FeedbackRepository extends MongoRepository<Feedback, Feedback> {
	void save(String i);
}
