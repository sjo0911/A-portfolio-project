package com.jonasson.eshop.bt.repositories;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Convention;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.helpers.IMongoClientProvider;
import com.jonasson.eshop.dt.DTOs.OrderDTO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class OrderMongoRepository implements IOrderRepository {
	
	private @Inject IMongoClientProvider mongoClientProvider;
	private MongoClient mongoClient;
	private MongoDatabase db;
	private MongoCollection<OrderDTO> collection;
	
	@PostConstruct
	private void init() {
		mongoClient = mongoClientProvider.getMongoClient();
		db = mongoClient.getDatabase("eshop");
		List<Convention> conventionList = new ArrayList<Convention>();
		conventionList.add(Conventions.ANNOTATION_CONVENTION);
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().conventions(conventionList).automatic(true).build()));
		collection = db.getCollection("order", OrderDTO.class).withCodecRegistry(pojoCodecRegistry);
	}
	

	@Override
	public String post(OrderDTO orderDTO) {
		String id = UUID.randomUUID().toString();
		orderDTO.setId(id);
		collection.insertOne(orderDTO);
		return id.toString();
	}
	
	@Override
	public OrderDTO get(String id) {
		return collection.find(Filters.eq(id)).first();
	}
	
	@Override
	public List<OrderDTO> getAll() {
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();
		Iterator<OrderDTO> cursor = collection.find().iterator();

		while (cursor.hasNext()) {
			orderList.add(cursor.next());
		}
		return orderList;
	}
	
	@Override
	public String update(OrderDTO orderDTO) throws NotFoundException {
		if( orderDTO == null || orderDTO.getId() == null )
			throw new NotFoundException("Could not update.");
		collection.findOneAndReplace(Filters.eq(orderDTO.getId()), orderDTO);
		return "Order updated!";
	}
	
	@Override
	public String delete(String id) throws NotFoundException {
		if(collection.findOneAndDelete(Filters.eq(id)) == null)
			throw new NotFoundException("Could not find order to delete");
		return "Order deleted";
	}

}
