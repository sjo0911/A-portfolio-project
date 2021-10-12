package com.jonasson.eshopJsf.dt.clients;

import java.util.List;

import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;

public interface IOrderClient {

	void post(OrderDTO order) throws DBException;

	List<OrderDTO> getAll();

}