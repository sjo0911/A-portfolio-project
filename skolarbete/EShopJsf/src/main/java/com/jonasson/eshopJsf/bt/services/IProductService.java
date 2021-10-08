package com.jonasson.eshopJsf.bt.services;

import java.util.List;

import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.dt.models.Product;

public interface IProductService {

	Product get(String id);

	List<Product> getAll();
	
	int addOne(String id) throws DBException;
	
	public int removeOne(String id) throws DBException;
	
	public int addMany(String id, int amount) throws DBException;

}