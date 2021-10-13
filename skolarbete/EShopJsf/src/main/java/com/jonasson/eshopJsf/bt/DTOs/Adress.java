package com.jonasson.eshopJsf.bt.DTOs;

import javax.validation.constraints.NotBlank;

public class Adress {
	@NotBlank(message = "Gata krävs")
	private String street;
	@NotBlank(message = "Gatunummer krävs")
	private String streetNumber;
	@NotBlank(message = "Stad krävs")
	private String city;
	@NotBlank(message = "Land krävs")
	private String country;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
