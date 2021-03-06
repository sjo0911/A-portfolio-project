package com.jonasson.eshopJsf.bt.services;

import java.util.List;

import com.jonasson.eshopJsf.bt.DTOs.ProductDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;

public interface IProductService {

	ProductDTO get(String id);

	List<ProductDTO> getAll();
	
	int addOne(String id) throws DBException;
	
	public int removeOne(String id) throws DBException;
	
	public int addMany(String id, int amount) throws DBException;

}