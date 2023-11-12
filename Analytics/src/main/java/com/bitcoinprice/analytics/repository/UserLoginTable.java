package com.bitcoinprice.analytics.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bitcoinprice.dataparsing.user.Login;


@Repository
public interface UserLoginTable extends MongoRepository<Login, String> {
	
	public Login findByemail(String email);

	public Login findBy_id(ObjectId sessionIdObj);

	
}