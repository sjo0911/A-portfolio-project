package com.jonasson.eshopJsf.bt.DTOs;

public class OrderItem {
	private String productId;
	private int amount;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
