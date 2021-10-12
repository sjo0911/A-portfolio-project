package com.jonasson.eshopJsf.bt.DTOs;

public class CartItem {
	private int amount;
	private ProductDTO product;
	
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	
	public double totalAmount() {
		return product.getPrice() * amount;
	}
}