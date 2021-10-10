package com.jonasson.eshop.dt.daos;

import java.util.List;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.dt.enteties.Product;

public interface IProductDAO {
	void post(Product product);

	Product get(String id);

	String update(Product product, String id) throws NotFoundException;

	String delete(String id) throws NotFoundException;

	<T> List<T> getAll();

}