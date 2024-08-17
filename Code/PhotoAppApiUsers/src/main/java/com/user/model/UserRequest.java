package com.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRequest {
	
	@NotNull(message = "first name cannot be null")
	@Size(min =2 , message = "Firstname cannot be less than two characters")
	private String firstName;
	
	@NotNull(message = "last name cannot be null")
	@Size(min =2 , message = "last cannot be less than two characters")
	private String lastName;
	
	@NotNull(message = "email cannot be null")
	@Email
	private String email;
	
	@NotNull(message = "pssword cannot be null")
	@Size(min =8 , max = 16, message = "password must be greater than 8 and less than 16")
	private String password;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
