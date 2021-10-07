package com.jonasson.eshopJsf.bt.services;

import com.jonasson.eshop.bt.exceptions.DBException;
import com.jonasson.eshopJsf.dt.models.Order;

public interface IOrderService {

	void post(Order order) throws DBException;

}