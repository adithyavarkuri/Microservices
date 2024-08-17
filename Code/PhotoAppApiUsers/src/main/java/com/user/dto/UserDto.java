package com.user.dto;

import java.io.Serializable;
import java.util.List;

import com.user.model.AlbumResponseModel;

public class UserDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8596029065171815662L;

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;

	private String UserId;
	
	private String encryptedPassword;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public List<AlbumResponseModel> getAlbums() {
		return albums;
	}

	public void setAlbums(List<AlbumResponseModel> albums) {
		this.albums = albums;
	}
	
	
	
	
}
