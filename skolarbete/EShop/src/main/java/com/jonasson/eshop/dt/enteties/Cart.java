package com.jonasson.eshop.dt.enteties;

import java.util.List;

public class Cart {
	private String _id;
	private List<OrderItem> productList;
	
	public Cart() {
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<OrderItem> getProductList() {
		return productList;
	}
	public void setProductList(List<OrderItem> productList) {
		this.productList = productList;
	}

}
