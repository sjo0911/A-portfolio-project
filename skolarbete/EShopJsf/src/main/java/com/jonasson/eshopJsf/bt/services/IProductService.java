package com.jonasson.eshopJsf.bt.services;

import java.util.List;

import com.jonasson.eshopJsf.dt.models.Product;

public interface IProductService {

	Product get(String id);

	List<Product> getAll();

}