package com.user.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.UserDto;
import com.user.model.UserRequest;
import com.user.model.UserResponse;
import com.user.service.UsersService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	UsersService userService;
	
	@GetMapping("status/check")
	public String getStatus() {
		return "working on port" + env.getProperty("local.server.port") + "with token = " + env.getProperty("token.secret");
		
	}
	
	@PostMapping
	public ResponseEntity createUser(@Valid @RequestBody UserRequest userDetails) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto user = modelMapper.map(userDetails, UserDto.class);
		UserDto userDto = userService.createUser(user);
	UserResponse response = modelMapper.map(userDto, UserResponse.class);
		return  ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	 @GetMapping(value="/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	   // @PreAuthorize("hasRole('ADMIN') or principal == #userId")
	    //@PreAuthorize("principal == #userId")
	    //@PostAuthorize("principal == returnObject.body.userId")
	    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId, 
	    		@RequestHeader("Authorization") String authorization) {
	       
			/* UserDto userDto = userService.getUserByUserId(userId, authorization); */
		 UserDto userDto = userService.getUserByUserId(userId); 
	        UserResponse returnValue = new ModelMapper().map(userDto, UserResponse.class);
	        
	        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	    }

}
