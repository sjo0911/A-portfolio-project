package com.jonasson.eshop.bt.DTOs;

import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;

import com.jonasson.eshop.dt.enteties.OrderItem;

public class CartDTO {
	
	@BsonId String id;
	private String customerId;
	private List<OrderItem> productList;
	
	public CartDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<OrderItem> getProductList() {
		return productList;
	}
	public void setProductList(List<OrderItem> productList) {
		this.productList = productList;
	}

}
