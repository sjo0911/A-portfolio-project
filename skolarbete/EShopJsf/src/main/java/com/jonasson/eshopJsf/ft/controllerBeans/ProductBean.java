package com.jonasson.eshopJsf.ft.controllerBeans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.jonasson.eshopJsf.bt.DTOs.ProductDTO;
import com.jonasson.eshopJsf.bt.services.IProductService;
import com.jonasson.eshopJsf.bt.services.ProductService;

@Named("productBean")
@RequestScoped
public class ProductBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IProductService productService = new ProductService();
	
	public List<ProductDTO> getAll(){
		return productService.getAll();
	}
	
	public ProductDTO get(String id) {
		return productService.get(id);
	}

}


