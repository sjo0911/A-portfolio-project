package com.jonasson.eshop.bt.repositories;

import java.util.List;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.dt.DTOs.CartDTO;


public interface ICartRepository {

	String post(CartDTO cart);

	CartDTO get(String id);

	List<CartDTO> getAll();

	String update(CartDTO cartDTO) throws NotFoundException;

	String delete(String id) throws NotFoundException;

}