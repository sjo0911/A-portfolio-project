package com.jonasson.eshop.dt.enteties;

import java.util.List;

public class Order {
	private String id;
	private Customer customer;
	private List<OrderItem> productToOrderList;
	
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
	public List<OrderItem> getProductToOrderList() {
		return productToOrderList;
	}
	public void setProductToOrderList(List<OrderItem> productToOrderList) {
		this.productToOrderList = productToOrderList;
	}
	
}
