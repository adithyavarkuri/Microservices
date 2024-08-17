package com.request;

import jakarta.validation.constraints.NotNull;

public class UpdateUserDetailsRequest {
	
	@NotNull(message =  "First Name cannot be null")
	private String firstName;
	@NotNull
	private String lastNam;

	
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
	
}
