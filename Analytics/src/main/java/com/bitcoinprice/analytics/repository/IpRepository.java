package com.bitcoinprice.analytics.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bitcoinprice.analytics.localmodel.UserIPInformation;

@Repository
public interface IpRepository extends MongoRepository<UserIPInformation, String> {
	public UserIPInformation findByipv4(String ipv4);
	public UserIPInformation findByipv6(String ipv6);
	public void deleteBy_id(ObjectId _id);
	
}