package com.jonasson.eshop.dt.daos;

import javax.annotation.PostConstruct;

import com.jonasson.eshop.dt.enteties.Order;

public class OrderDAO extends DAOAbstract<Order> implements DAOInterface, IOrderDAO {
	
	@PostConstruct 
	protected void init() {
		setCollection("eshop", "order");
	}
	
}

