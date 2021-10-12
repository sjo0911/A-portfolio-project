package com.jonasson.eshopJsf.bt.services;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import com.jonasson.eshopJsf.bt.DTOs.ProductDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.dt.clients.IProductClient;
import com.jonasson.eshopJsf.dt.clients.ProductClient;

@RequestScoped
public class ProductService implements IProductService {
	
	private IProductClient productClient = new ProductClient();
	
	@PostConstruct
	private void init() {
		productClient = new ProductClient();
	}

	public ProductService() {
	}
	
	@Override
	public ProductDTO get(String id) {
		return productClient.get(id);
	}
	
	@Override
	public List<ProductDTO> getAll() {
		return productClient.getAll();
	}
	
	@Override
	public int addOne(String id) throws DBException {
		return productClient.addOne(id);
	}
	
	@Override
	public int addMany(String id, int amount) throws DBException {
		return productClient.addMany(id, amount);
	}
	
	@Override
	public int removeOne(String id) throws DBException {
		return productClient.removeOne(id);
	}

}
