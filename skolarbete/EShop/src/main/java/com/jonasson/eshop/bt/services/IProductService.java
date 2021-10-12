package com.jonasson.eshop.bt.services;

import java.util.List;

import com.jonasson.eshop.bt.DTOs.ProductDTO;
import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.exceptions.ValidationException;

public interface IProductService {

	String post(ProductDTO productDTO) throws ValidationException;

	ProductDTO get(String id);

	String delete(String id) throws NotFoundException ;

	List<ProductDTO> getAll();

	String update(ProductDTO productDTO) throws NotFoundException, ValidationException ;

}