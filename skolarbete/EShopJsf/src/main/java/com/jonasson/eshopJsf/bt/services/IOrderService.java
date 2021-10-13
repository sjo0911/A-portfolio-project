package com.jonasson.eshopJsf.bt.services;

import java.util.List;

import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.bt.exceptions.ValidationException;
import com.jonasson.ft.modelBeans.OrderModel;

public interface IOrderService {

	void post(OrderModel order) throws DBException, ValidationException;
	
	public List<OrderModel> getAll();

}