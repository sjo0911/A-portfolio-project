package com.jonasson.eshop.bt.repositories;

import java.util.List;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.dt.DTOs.OrderDTO;

public interface IOrderRepository {

	String post(OrderDTO orderDTO);

	OrderDTO get(String id);

	List<OrderDTO> getAll();

	String update(OrderDTO orderDTO) throws NotFoundException;

	String delete(String id) throws NotFoundException;

}