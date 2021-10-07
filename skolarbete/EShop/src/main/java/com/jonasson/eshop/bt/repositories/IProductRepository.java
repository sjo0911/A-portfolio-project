package com.jonasson.eshop.bt.repositories;

import java.util.List;

import com.jonasson.eshop.dt.DTOs.ProductDTO;
import com.mongodb.WriteResult;

public interface IProductRepository {

	WriteResult post(ProductDTO product);

	ProductDTO get(String id);

	WriteResult delete(String id);

	List<ProductDTO> getAll();

	WriteResult update(ProductDTO product);

}