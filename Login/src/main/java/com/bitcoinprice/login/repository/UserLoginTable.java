package com.bitcoinprice.login.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bitcoinprice.dataparsing.user.Login;


@Repository
public interface UserLoginTable extends MongoRepository<Login, String> {
	
	public Login findByemail(String email);

	public ObjectId findBy_id(ObjectId sessionIdObj);

	
}