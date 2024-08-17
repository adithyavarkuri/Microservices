package com.user.model;

import java.util.List;

public class UserResponse {
	
private String firstName;
	
	private String lastName;
	
	private String email;


	private String UserId;
	
	private List<AlbumResponseModel> albums;


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


	public String getUserId() {
		return UserId;
	}


	public void setUserId(String userId) {
		UserId = userId;
	}


	public List<AlbumResponseModel> getAlbums() {
		return albums;
	}


	public void setAlbums(List<AlbumResponseModel> albums) {
		this.albums = albums;
	}
	
	
	

}
