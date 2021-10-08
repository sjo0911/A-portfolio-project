package com.jonasson.eshopJsf.bt.services;

import java.util.List;

import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.dt.models.Order;

public interface IOrderService {

	void post(Order order) throws DBException;
	
	public List<Order> getAll();

}