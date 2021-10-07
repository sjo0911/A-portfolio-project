package com.jonasson.eshop.bt.helpers;

import com.mongodb.MongoClient;

public interface IMongoClientProvider {

	MongoClient getMongoClient();

	void init();

}