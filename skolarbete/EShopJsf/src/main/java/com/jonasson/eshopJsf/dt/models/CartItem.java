package com.jonasson.eshopJsf.dt.models;

public class CartItem {
	private int amount;
	private Product product;
	
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public double totalAmount() {
		return product.getPrice() * amount;
	}
}