package com.jonasson.eshop.bt.helpers;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoClientProvider implements IMongoClientProvider {
	
	private MongoClient mongoClient = null;
	
	@Override
	@Lock(LockType.READ)
	public MongoClient getMongoClient(){
		return mongoClient;
	}
	
	@Override
	@PostConstruct
	public void init() {
		String mongoIpAddress = "mongodb+srv://sven:eriksson@cluster0.2si5o.mongodb.net/eshop?retryWrites=true&w=majority";
		MongoClientURI mongoUri = new MongoClientURI(mongoIpAddress);
		mongoClient = new MongoClient(mongoUri);
	}
}