package com.jonasson.eshopJsf.bt.services;

import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;

public interface IMQOrderService {

	void post(OrderDTO orderDTO);

}