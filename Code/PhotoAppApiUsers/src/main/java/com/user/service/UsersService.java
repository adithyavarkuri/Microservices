package com.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.user.dto.UserDto;

public interface UsersService extends UserDetailsService{
	
	public UserDto createUser(UserDto userDetails);
	
	public UserDto getUserDetailsByEmail(String email);
	UserDto getUserByUserId(String userId);

}
