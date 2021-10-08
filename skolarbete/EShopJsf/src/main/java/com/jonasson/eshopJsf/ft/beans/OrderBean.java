package com.jonasson.eshopJsf.ft.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.bt.services.IOrderService;
import com.jonasson.eshopJsf.bt.services.OrderService;
import com.jonasson.eshopJsf.dt.models.Adress;
import com.jonasson.eshopJsf.dt.models.CartItem;
import com.jonasson.eshopJsf.dt.models.Customer;
import com.jonasson.eshopJsf.dt.models.Order;
import com.jonasson.eshopJsf.dt.models.OrderItem;

@Named("orderBean")
@RequestScoped
public class OrderBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Order order = new Order();
	private IOrderService orderService = new OrderService();
	private String orderCompleted = null;
	
	@PostConstruct
	private void init() {
		order.setCustomer(new Customer());
		order.getCustomer().setAdress(new Adress());
	}
	
	public void makeOrder(List<CartItem> cartItemList) {
		List<OrderItem> orderItems = new ArrayList<>();
		cartItemList.forEach(cartItem -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setAmount(cartItem.getAmount());
			orderItem.setProductId(cartItem.getProduct().getId());
			orderItems.add(orderItem);
		});
		order.setOrderItemList(orderItems);
		try {
			orderService.post(order);
			setOrderCompleted("Tack för din order");
		} catch (DBException exception) {
			setOrderCompleted("Nånting gick fel. Försök igen senare");
		}
	}
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}

	public String getOrderCompleted() {
		return orderCompleted;
	}

	public void setOrderCompleted(String orderCompleted) {
		this.orderCompleted = orderCompleted;
	}
}
