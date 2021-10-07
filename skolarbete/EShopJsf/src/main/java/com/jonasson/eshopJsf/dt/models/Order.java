package com.jonasson.eshopJsf.dt.models;

import java.util.List;

public class Order {
	private String id;
	private Customer customer;
	private List<OrderItem> orderItemList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> productToOrderList) {
		this.orderItemList = productToOrderList;
	}
	
}