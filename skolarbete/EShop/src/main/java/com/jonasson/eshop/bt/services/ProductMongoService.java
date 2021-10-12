package com.jonasson.eshop.bt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.jonasson.eshop.bt.DTOs.ProductDTO;
import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.exceptions.ValidationException;
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
	public String post(ProductDTO productDTO) throws ValidationException {
		Product product = toProduct(productDTO);
		String id = UUID.randomUUID().toString();
		product.setId(id);
		validateProduct(product);
		productDAO.post(product);
		return id;
	}
	
	@Override
	public ProductDTO get(String id) {
		return toProductDTO(productDAO.get(id));
	}
	
	@Override
	public List<ProductDTO> getAll() {
		List<Product> products = productDAO.getAll();
		List<ProductDTO> productDTOs = new ArrayList<>();
		products.forEach(prod -> productDTOs.add(toProductDTO(prod)));
		return productDTOs;
	}
	
	@Override
	public String update(ProductDTO productDTO) throws NotFoundException, ValidationException {
		Product product = toProduct(productDTO);
		validateProduct(product);
		return productDAO.update(product, product.getId());
	}
	
	@Override
	public String delete(String id) throws NotFoundException {
		return productDAO.delete(id);
	}
	
	private void validateProduct(Product product) throws ValidationException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Product>> orderValidationSet = validator.validate(product);
		if(orderValidationSet.size() > 0) {
			System.out.println(orderValidationSet.toArray()[0].toString());
			throw new ValidationException(orderValidationSet.toArray()[0].toString());
		}
	}
	
	private ProductDTO toProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());
		productDTO.setStock(product.getStock());
		productDTO.setDescription(product.getDescription());
		return productDTO;
	}
	
	private Product toProduct(ProductDTO productDTO) {
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setStock(productDTO.getStock());
		product.setDescription(productDTO.getDescription());
		return product;
	}
	
}
