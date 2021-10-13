package com.jonasson.eshop.bt.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.jonasson.eshop.bt.DTOs.OrderDTO;
import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.exceptions.ValidationException;
import com.jonasson.eshop.dt.daos.IOrderDAO;
import com.jonasson.eshop.dt.enteties.Order;


public class OrderMongoService implements IOrderService {
	
	private @Inject IOrderDAO orderDAO;
	
	@PostConstruct
	private void init() {
	}

	@Override
	public String post(OrderDTO orderDTO) throws ValidationException {
		Order order = toOrder(orderDTO);
		String orderId = setIds(order);
		validateOrder(order);
		orderDAO.post(order);
		return orderId;
	}
	
	@Override
	public OrderDTO get(String id) {
		return toOrderDTO(orderDAO.get(id));
	}
	
	@Override
	public List<OrderDTO> getAll() {
		List<Order> orderList = orderDAO.getAll();
		List<OrderDTO> orderDTOsList = new ArrayList<>();
		orderList.forEach(order -> orderDTOsList.add(toOrderDTO(order)));
		return orderDTOsList;
	}
	
	@Override
	public String update(OrderDTO orderDTO) throws NotFoundException, ValidationException {
		Order order = toOrder(orderDTO);
		validateOrder(order);
		return orderDAO.update(order, order.getId());
	}
	
	@Override
	public String delete(String id) throws NotFoundException {
		return orderDAO.delete(id);
	}
	
	private Order toOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setCustomer(orderDTO.getCustomer());
		order.setId(orderDTO.getId());
		order.setOrderItemList(orderDTO.getOrderItemList());
		return order;
	}
	
	private OrderDTO toOrderDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setCustomer(order.getCustomer());
		orderDTO.setId(order.getId());
		orderDTO.setOrderItemList(order.getOrderItemList());
		return orderDTO;
	}
	
	private String setIds(Order order) {
		String id = UUID.randomUUID().toString();
		order.setId(id);
		if(order.getCustomer().getId() == null || order.getCustomer().getId().isEmpty()) {
			order.getCustomer().setId(UUID.randomUUID().toString());
		}
		// return order id
		return id;
	}
	
	private void validateOrder(Order order) throws ValidationException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Order>> orderValidationSet = validator.validate(order);
		if(orderValidationSet.size() > 0) {
			throw new ValidationException(orderValidationSet.toArray()[0].toString());
		}
	}
	

}
