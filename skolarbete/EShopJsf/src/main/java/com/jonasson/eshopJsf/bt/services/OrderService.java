package com.jonasson.eshopJsf.bt.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.bt.exceptions.ValidationException;
import com.jonasson.eshopJsf.dt.clients.IOrderClient;
import com.jonasson.eshopJsf.dt.clients.OrderClient;
import com.jonasson.ft.modelBeans.OrderModel;
@RequestScoped
public class OrderService implements IOrderService {
	
	private IOrderClient orderClient = new OrderClient();
	
	@Override
	public void post(OrderModel order) throws DBException, ValidationException {
		validateOrder(order);
		orderClient.post(toOrderDTO(order));
	}
	
	@Override
	public List<OrderModel> getAll() {
		List<OrderModel> orderModelList = new ArrayList<>();
		List<OrderDTO> orderDTOList = orderClient.getAll();
		orderDTOList.forEach(orderDTO -> orderModelList.add(toOrderModel(orderDTO)));
		return orderModelList;
	}
	
	private OrderModel toOrderModel(OrderDTO orderDTO) {
		OrderModel order = new OrderModel();
		order.setCustomer(orderDTO.getCustomer());
		order.setId(orderDTO.getId());
		order.setOrderItemList(orderDTO.getOrderItemList());
		return order;
	}
	
	private OrderDTO toOrderDTO(OrderModel order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setCustomer(order.getCustomer());
		orderDTO.setId(order.getId());
		orderDTO.setOrderItemList(order.getOrderItemList());
		return orderDTO;
	}
	
	private void validateOrder(OrderModel order) throws ValidationException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<OrderModel>> orderValidationSet = validator.validate(order);
		if(orderValidationSet.size() > 0) {
			//Mindre snygg lösning för att få fram det meddelande jag vill visa. Bättre väg hade varit att göra någon regex lösning
			String exceptionMessage = orderValidationSet.toArray()[0].toString();
			String formatedMessage = exceptionMessage.substring(exceptionMessage.indexOf('=', exceptionMessage.indexOf("messageTemplate"))).substring(2);
			formatedMessage = formatedMessage.subSequence(0, formatedMessage.length() -2).toString();
			throw new ValidationException(formatedMessage);
		}
	}
}
