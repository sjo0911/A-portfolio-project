package com.jonasson.eshopJsf.ft.controllerBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jonasson.eshopJsf.bt.DTOs.Adress;
import com.jonasson.eshopJsf.bt.DTOs.CartItem;
import com.jonasson.eshopJsf.bt.DTOs.Customer;
import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;
import com.jonasson.eshopJsf.bt.DTOs.OrderItem;
import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.bt.services.IMQOrderService;
import com.jonasson.eshopJsf.bt.services.IOrderService;

@Named("orderBean")
@RequestScoped
public class OrderBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderDTO order = new OrderDTO();
	private @Inject IOrderService orderService;
	private @Inject IMQOrderService mqOrderService;
	private @Inject CartBean cartBean; 
	private String orderCompleted = null;
	
	@PostConstruct
	private void init() {
		order.setCustomer(new Customer());
		order.getCustomer().setAdress(new Adress());
	}
	
	public List<OrderDTO> getAll(){
		return orderService.getAll();
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
			cartBean.clearCartAfterOrderCompleted();
		} catch (DBException exception) {
			setOrderCompleted("Nånting gick fel. Försök igen senare");
		}
	}
	
	public void makeOrderMq(List<CartItem> cartItemList) {
		List<OrderItem> orderItems = new ArrayList<>();
		cartItemList.forEach(cartItem -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setAmount(cartItem.getAmount());
			orderItem.setProductId(cartItem.getProduct().getId());
			orderItems.add(orderItem);
		});
		order.setOrderItemList(orderItems);
		mqOrderService.post(order);
		setOrderCompleted("Order skickad");
		cartBean.clearCartAfterOrderCompleted();
	}
	
	public OrderDTO getOrder() {
		return order;
	}
	public void setOrder(OrderDTO order) {
		this.order = order;
	}

	public String getOrderCompleted() {
		return orderCompleted;
	}

	public void setOrderCompleted(String orderCompleted) {
		this.orderCompleted = orderCompleted;
	}
}
