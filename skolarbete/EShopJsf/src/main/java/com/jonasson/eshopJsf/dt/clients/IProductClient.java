package com.jonasson.eshopJsf.dt.clients;

import java.util.List;

import com.jonasson.eshopJsf.bt.DTOs.ProductDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;

public interface IProductClient {

	ProductDTO get(String id);

	List<ProductDTO> getAll();

	int addOne(String id) throws DBException;

	int addMany(String id, int amount) throws DBException;

	int removeOne(String id) throws DBException;

}