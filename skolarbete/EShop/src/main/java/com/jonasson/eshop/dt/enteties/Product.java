package com.jonasson.eshop.dt.enteties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.codecs.pojo.annotations.BsonId;

public class Product {
	@NotBlank(message = "Namn krävs")
	private String name;
	@NotBlank(message = "Id krävs")
	@BsonId private String id;
	@NotBlank(message = "Beskrivning krävs")
	private String description;
	@NotNull
	private int stock;
	@NotNull
	private double price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}



}
