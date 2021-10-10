package com.jonasson.eshop.dt.daos;

import java.util.List;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.dt.enteties.Order;

public interface IOrderDAO {

	void post(Order order);

	Order get(String id);

	String update(Order orderDTO, String id) throws NotFoundException;

	String delete(String id) throws NotFoundException;

	<T> List<T> getAll();

}