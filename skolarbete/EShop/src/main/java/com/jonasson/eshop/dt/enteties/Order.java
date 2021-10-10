package com.jonasson.eshop.dt.enteties;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.bson.codecs.pojo.annotations.BsonId;
@Entity
public class Order implements EntetyInterface {
	@NotBlank(message = "Id kunde inte hittas")
	@BsonId private String id;
	@NotNull(message = "Ordern behöver en kund för att kunna genomföras")
	@Valid
	private Customer customer;
	@NotEmpty(message = "Ordern måste innehålla varor i orderkorgen!")
	private List<@Valid OrderItem> orderItemList;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

}
