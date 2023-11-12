package com.bitcoinprice.analytics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bitcoinprice.dataparsing.analytic.Provendata;

@Repository
@Transactional
public interface ProvendataRepository extends MongoRepository<Provendata, String> {
	void save(String i);
}