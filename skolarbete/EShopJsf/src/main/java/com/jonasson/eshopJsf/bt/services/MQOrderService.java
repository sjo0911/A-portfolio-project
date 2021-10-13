package com.jonasson.eshopJsf.bt.services;


import javax.enterprise.context.RequestScoped;
import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;
import com.jonasson.eshopJsf.dt.clients.IMQOrderClient;
import com.jonasson.eshopJsf.dt.clients.MQOrderClient;

@RequestScoped
public class MQOrderService implements IMQOrderService {
	IMQOrderClient orderClient = new MQOrderClient();
	
	@Override
	public void post(OrderDTO orderDTO) {
		orderClient.post(orderDTO);
	}
	

}
