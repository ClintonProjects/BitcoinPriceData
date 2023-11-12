package com.bitcoinprice.analytics.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bitcoinprice.dataparsing.user.Login;
import com.bitcoinprice.dataparsing.user.UserSession;

@Repository
public interface SessionIdTable extends MongoRepository<UserSession, String> {

//	Optional<UserSession> findById(ObjectId _id);

//	public UserSession findBy_id(ObjectId _id);

}