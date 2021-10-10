package com.jonasson.eshop.bt.services;

import java.util.List;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.dt.enteties.Product;

public interface IProductService {

	String post(Product product);

	Product get(String id);

	String delete(String id) throws NotFoundException ;

	List<Product> getAll();

	String update(Product product) throws NotFoundException ;

}