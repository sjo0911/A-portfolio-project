package com.jonasson.eshop.dt.enteties;

public class OrderItem {
	private String productId;
	private int amount;
	
	public OrderItem() {
		
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
