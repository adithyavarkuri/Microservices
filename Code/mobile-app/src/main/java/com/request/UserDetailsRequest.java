package com.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDetailsRequest {
	
	@NotNull(message =  "First Name cannot be null")
	private String firstName;
	@NotNull
	private String lastNam;
	@NotNull
	@Email(message = "not a valid email format")
	private String email;
	@Size(min =8, max = 20)
	@NotNull
	private String userId;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastNam() {
		return lastNam;
	}
	public void setLastNam(String lastNam) {
		this.lastNam = lastNam;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
