package com.jonasson.eshopJsf.bt.DTOs;

import java.util.List;

public class OrderDTO {
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