package com.jonasson.eshopJsf.bt.DTOs;

import javax.validation.constraints.NotNull;

public class OrderItem {
	@NotNull(message = "Produkter behöver id.")
	private String productId;
	@NotNull(message = "Produkter behöver antal beställda.")
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
