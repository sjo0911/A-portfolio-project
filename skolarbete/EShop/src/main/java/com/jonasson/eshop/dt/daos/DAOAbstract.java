package com.jonasson.eshop.dt.daos;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Convention;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.dt.enteties.Order;
import com.jonasson.eshop.dt.enteties.Product;
import com.jonasson.eshop.dt.helpers.IMongoClientProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public abstract class DAOAbstract<T> {
	protected MongoCollection<T> collection;
	
	private @Inject IMongoClientProvider mongoClientProvider;
	
	protected void setCollection(String databaseName, String collectionName) {
		MongoDatabase db = retriveMongoDatabase(databaseName);
		CodecRegistry pojoCodecRegistry = retriveCodecRegistryConfig();
		if(collectionName.equals("order")) {
			collection = (MongoCollection<T>) db.getCollection(collectionName, Order.class).withCodecRegistry(pojoCodecRegistry);
		} else if(collectionName.equals("productcollection")) {
			collection = (MongoCollection<T>) db.getCollection(collectionName, Product.class).withCodecRegistry(pojoCodecRegistry);
		}

	}
	
	private CodecRegistry retriveCodecRegistryConfig() {
		List<Convention> conventionList = new ArrayList<Convention>();
		conventionList.add(Conventions.ANNOTATION_CONVENTION);
		return fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().conventions(conventionList).automatic(true).build()));
	}
	
	private MongoDatabase retriveMongoDatabase(String databaseName) {
		MongoClient mongoClient = this.mongoClientProvider.getMongoClient();
		return mongoClient.getDatabase("eshop");
	}
	
	public <T> List<T> getAll() {
		List<T> orderList = new ArrayList<T>();
		MongoCursor<T> cursor = (MongoCursor<T>) collection.find().iterator();
		while (cursor.hasNext()) {
			orderList.add(cursor.next());
		}
		return orderList;
	}
	
	public T get(String id) {
		return collection.find(Filters.eq(id)).first();
	}
	
	public void post(T t) {
		collection.insertOne(t);
	}
	
	public String update(T t, String id) throws NotFoundException {
		if( t == null)
			throw new NotFoundException("Could not update.");
		collection.findOneAndReplace(Filters.eq(id), t);
		return "Updated!";
	}
	
	public String delete(String id) throws NotFoundException {
		if(collection.findOneAndDelete(Filters.eq(id)) == null)
			throw new NotFoundException("Could not find order to delete");
		return "Deleted";
	}
	
}
