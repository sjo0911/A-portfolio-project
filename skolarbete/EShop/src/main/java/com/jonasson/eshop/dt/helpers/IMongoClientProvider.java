package com.jonasson.eshop.dt.helpers;

import com.mongodb.MongoClient;

public interface IMongoClientProvider {

	MongoClient getMongoClient();

	void init();

}