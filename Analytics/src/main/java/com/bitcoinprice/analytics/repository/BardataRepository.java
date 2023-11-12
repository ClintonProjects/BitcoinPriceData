package com.bitcoinprice.analytics.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bitcoinprice.dataparsing.analytic.Bardata;


@Repository
@Transactional
public interface BardataRepository extends MongoRepository<Bardata, Bardata> {

	void save(String i);
	public List<Bardata> findAll();
}