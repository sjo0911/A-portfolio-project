package com.jonasson.eshopJsf.bt.services;

import com.jonasson.eshopJsf.bt.exceptions.ValidationException;
import com.jonasson.ft.modelBeans.OrderModel;

public interface IMQOrderService {

	void post(OrderModel order) throws ValidationException;

}