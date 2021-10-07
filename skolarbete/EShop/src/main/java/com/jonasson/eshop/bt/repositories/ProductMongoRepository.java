package com.jonasson.eshop.bt.repositories;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.jonasson.eshop.bt.helpers.DBObjectConverter;
import com.jonasson.eshop.bt.helpers.IMongoClientProvider;
import com.jonasson.eshop.dt.DTOs.ProductDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
@Stateless
public class ProductMongoRepository implements IProductRepository {

	private @EJB IMongoClientProvider mongoClientProvider;
	private MongoClient mongoClient;
	private DB db;
	private DBCollection collection;
	
	//Using old Mongo framework
	
	public ProductMongoRepository() {
	}
	@PostConstruct
	private void init() {
		mongoClient = mongoClientProvider.getMongoClient();
		db = mongoClient.getDB("eshop");
		collection = db.getCollection("productcollection");
	}
	
	@Override
	public WriteResult post(ProductDTO product) {
		String id = UUID.randomUUID().toString();
		product.setId(id);
		DBObject productToPost = DBObjectConverter.toDBObject(product);
		return collection.insert(productToPost);
	}
	
	@Override
	public ProductDTO get(String id) {
		return DBObjectConverter.toProductDTO(collection.findOne(new BasicDBObject("_id", id)));
	}
	
	@Override
	public WriteResult delete(String id) {
		return collection.remove(new BasicDBObject("_id", id));
	}
	
	@Override
	public List<ProductDTO> getAll() {
		List<ProductDTO> productList = new ArrayList<ProductDTO>();
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			ProductDTO p = DBObjectConverter.toProductDTO(doc);
			productList.add(p);
		}
		return productList;
	}
	
	@Override
	public WriteResult update(ProductDTO product) {
		return collection.update(new BasicDBObject("_id", product.getId()), DBObjectConverter.toDBObject(product));
	}
}
