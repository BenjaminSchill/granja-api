package com.granjas.granjaapi.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.granjas.granjaapi.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonPropertyOrder({
	"name",
	"email",
	"password"
})
public class UserDTO {
	
	@NotBlank(message = "Your name is mandatory")
	private String name;
	
	@NotBlank(message = "Your email is mandatory")
	@Email(message = "Enter a valid email address")
	private String email;
	
	@NotBlank(message = "Creating a password is mandatory")
	@Size(min = 8, message = "Your password must be longer than 8 characters")
	private String password;
	
	public UserDTO() { 
	}
	
	public UserDTO(User user) { 
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
