package com.jonasson.eshop.bt.DTOs;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;

import com.jonasson.eshop.dt.enteties.Customer;
import com.jonasson.eshop.dt.enteties.OrderItem;

public class OrderDTO {
	@BsonId private String id;
	private Customer customer;
	private List<OrderItem> orderItemList;
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

}
