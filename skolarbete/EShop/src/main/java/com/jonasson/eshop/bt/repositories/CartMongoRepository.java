package com.jonasson.eshop.bt.repositories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Convention;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.helpers.IMongoClientProvider;
import com.jonasson.eshop.dt.DTOs.CartDTO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class CartMongoRepository implements ICartRepository {

	//Using new Mongo framework
	
	private @Inject IMongoClientProvider mongoClientProvider;
	private MongoClient mongoClient;
	private MongoDatabase db;
	private MongoCollection<CartDTO> collection;
	
	public CartMongoRepository() {
	}
	
	@PostConstruct
	private void init() {
		mongoClient = mongoClientProvider.getMongoClient();
		db = mongoClient.getDatabase("eshop");
		List<Convention> conventionList = new ArrayList<Convention>();
		conventionList.add(Conventions.ANNOTATION_CONVENTION);
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().conventions(conventionList).automatic(true).build()));
		collection = db.getCollection("cart", CartDTO.class).withCodecRegistry(pojoCodecRegistry);
	}
	

	@Override
	public String post(CartDTO cart) {
		String id = UUID.randomUUID().toString();
		cart.setId(id);
		collection.insertOne(cart);
		return id.toString();
	}
	
	@Override
	public CartDTO get(String id) {
		return collection.find(Filters.eq(id)).first();
	}
	
	@Override
	public List<CartDTO> getAll() {
		List<CartDTO> cartList = new ArrayList<CartDTO>();
		Iterator<CartDTO> cursor = collection.find().iterator();

		while (cursor.hasNext()) {
			cartList.add(cursor.next());
		}
		return cartList;
	}
	
	@Override
	public String update(CartDTO cartDTO) throws NotFoundException {
		if( cartDTO == null || cartDTO.getId() == null )
			throw new NotFoundException("Could not update. Cart got no id");
		collection.findOneAndReplace(Filters.eq(cartDTO.getId()), cartDTO);
		return "Cart updated!";
	}
	
	@Override
	public String delete(String id) throws NotFoundException {
		if(collection.findOneAndDelete(Filters.eq(id)) == null)
			throw new NotFoundException("Could not find cart to delete");
		return "Cart deleted";
	}

}
