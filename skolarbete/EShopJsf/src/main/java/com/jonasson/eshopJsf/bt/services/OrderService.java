package com.jonasson.eshopJsf.bt.services;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.dt.clients.IOrderClient;
import com.jonasson.eshopJsf.dt.clients.OrderClient;
@RequestScoped
public class OrderService implements IOrderService {
	
	private IOrderClient orderClient = new OrderClient();
	
	@Override
	public void post(OrderDTO orderDTO) throws DBException {
		orderClient.post(orderDTO);
	}
	
	@Override
	public List<OrderDTO> getAll() {
		return orderClient.getAll();
	}
}
