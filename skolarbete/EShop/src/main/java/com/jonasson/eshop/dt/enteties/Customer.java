package com.jonasson.eshop.dt.enteties;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Customer {
	@NotBlank(message = "Kunde inte hitta id nummer")
	private String id;
	@NotBlank(message = "Förnamn krävs")
	private String firstName;
	@NotBlank(message = "Efternam krävs")
	private String lastName;
	@NotBlank(message = "Email krävs")
	@Email(message = "Email måste vara korrekt ifylld")
	private String email;
	@NotNull(message = "Adress krävs")
	@Valid
	private Adress adress;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Adress getAdress() {
		return adress;
	}
	public void setAdress(Adress adress) {
		this.adress = adress;
	}


}
