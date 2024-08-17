package com.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exceptions.UserException;
import com.model.UserDetails;
import com.request.UpdateUserDetailsRequest;
import com.request.UserDetailsRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
	
	Map<String, UserDetails> users = new HashMap<>();
	
	//http://localhost:8080/users/100
	@GetMapping(path ="/{userId}")
	public String getUser(@PathVariable String userId) {
		
		return "get user called for user id = " + userId;
	}
	
	@GetMapping(path ="/exception/{userId}")
	public String getExcUser(@PathVariable String userId) {
		//String firstName = null;
		//firstName.length();
		if(true) {
			throw new UserException("User exception created");
		}
		return "get user called for user id = " + userId;
	}
	
	
	//http://localhost:8080/users?page=1&limit=2
	@GetMapping()
	public String getUser(@RequestParam(value = "page", defaultValue = "1", required =false) int page,
			@RequestParam(value = "limit") int limit ) {
		return "get user called for page = " + page + " limit " + limit;
	}
	
	@GetMapping(path ="/getuser/{userId}", produces = {MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserDetails> getUserDetails(@PathVariable String userId) {
		UserDetails user = new UserDetails();
		user.setFirstName("Adithya");
		user.setLastNam("Vakuri");
		user.setEmail("adithya.va@mail.com");
		user.setUserId("adithya.varkuri");
		return new ResponseEntity<UserDetails>(user, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping( consumes = {MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE},
			 produces = {MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserDetails> createUser(@Valid @RequestBody UserDetailsRequest userDetails) {
		UserDetails user = new UserDetails();
		user.setFirstName(userDetails.getFirstName());
		user.setLastNam(userDetails.getLastNam());
		user.setEmail(userDetails.getEmail());
		
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);
		if(users == null) {
			users = new HashMap<>();	
		}
		users.put(userId, user);
		
		
		return new ResponseEntity<UserDetails>(user,HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(path = "/{userId}" , consumes = {MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE},
			 produces = {MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE})
	public String updateUser(@PathVariable String userId, @RequestBody UpdateUserDetailsRequest userDetails) {
		return "update user called";
	}
	
	@DeleteMapping(path = "/{userId}")
	public String deleteUser(@PathVariable String userID) {
		return "delete user called";
	}

}
