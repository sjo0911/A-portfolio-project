package com.jonasson.eshop.bt.helpers;

import com.jonasson.eshop.dt.DTOs.ProductDTO;
import com.jonasson.eshop.dt.enteties.Product;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class DBObjectConverter {
	public static DBObject toDBObject(ProductDTO p) {

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("name", p.getName())
				.append("description", p.getDescription())
				.append("stock", p.getStock())
				.append("price", p.getPrice());
		if (p.getId() != null)
			builder = builder.append("_id", p.getId());
		return builder.get();
	}

	public static Product toProduct(DBObject doc) {
		Product p = new Product();
		p.setName((String) doc.get("name"));
		p.setDescription((String) doc.get("description"));
		p.setId((String) doc.get("_id"));
		p.setPrice((double) doc.get("price"));
		p.setStock((int) doc.get("stock"));
		return p;

	}
	
	public static ProductDTO toProductDTO(DBObject doc) {
		ProductDTO p = new ProductDTO();
		p.setName((String) doc.get("name"));
		p.setDescription((String) doc.get("description"));
		p.setId((String) doc.get("_id"));
		p.setPrice((double) doc.get("price"));
		p.setStock((int) doc.get("stock"));
		return p;
	}
}
