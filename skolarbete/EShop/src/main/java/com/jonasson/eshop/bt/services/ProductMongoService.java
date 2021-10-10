package com.jonasson.eshop.bt.services;

import java.util.List;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.dt.daos.IProductDAO;
import com.jonasson.eshop.dt.enteties.Product;
@Stateless
public class ProductMongoService implements IProductService {

	
	private @Inject IProductDAO productDAO;
	
	public ProductMongoService() {
	}
	@PostConstruct
	private void init() {
	}
	
	@Override
	public String post(Product product) {
		String id = UUID.randomUUID().toString();
		product.setId(id);
		productDAO.post(product);
		return id;
	}
	
	@Override
	public Product get(String id) {
		return productDAO.get(id);
	}
	
	@Override
	public List<Product> getAll() {
		return productDAO.getAll();
	}
	
	@Override
	public String update(Product product) throws NotFoundException {
		if( product == null || product.getId() == null )
			throw new NotFoundException("Could not update.");
		return productDAO.update(product, product.getId());
	}
	
	@Override
	public String delete(String id) throws NotFoundException {
		return productDAO.delete(id);
	}
}
