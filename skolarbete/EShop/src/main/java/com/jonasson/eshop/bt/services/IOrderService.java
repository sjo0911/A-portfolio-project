package com.jonasson.eshop.bt.services;

import java.util.List;

import com.jonasson.eshop.bt.DTOs.OrderDTO;
import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.exceptions.ValidationException;
import com.jonasson.eshop.dt.enteties.Order;

public interface IOrderService {

	String post(OrderDTO orderDTO) throws ValidationException;

	OrderDTO get(String id);

	List<OrderDTO> getAll();

	String update(Order orderDTO) throws NotFoundException;

	String delete(String id) throws NotFoundException;

}