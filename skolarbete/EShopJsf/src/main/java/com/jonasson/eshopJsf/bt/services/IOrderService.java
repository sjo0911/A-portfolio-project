package com.jonasson.eshopJsf.bt.services;

import java.util.List;

import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;

public interface IOrderService {

	void post(OrderDTO order) throws DBException;
	
	public List<OrderDTO> getAll();

}