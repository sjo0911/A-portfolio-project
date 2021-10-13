package com.jonasson.eshopJsf.dt.clients;

import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;

public interface IMQOrderClient {

	void post(OrderDTO orderDTO);

}