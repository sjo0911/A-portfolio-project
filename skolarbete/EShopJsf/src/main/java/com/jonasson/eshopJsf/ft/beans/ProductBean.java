package com.jonasson.eshopJsf.ft.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.jonasson.eshopJsf.bt.services.IProductService;
import com.jonasson.eshopJsf.bt.services.ProductService;
import com.jonasson.eshopJsf.dt.models.Order;
import com.jonasson.eshopJsf.dt.models.Product;

@Named("productBean")
@RequestScoped
public class ProductBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IProductService productService = new ProductService();
	
	public List<Product> getAll(){
		return productService.getAll();
	}
	
	public Product get(String id) {
		return productService.get(id);
	}

}


