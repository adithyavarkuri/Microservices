package com.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccoutController {
	
	@GetMapping("/status/check")
	public String getStatus() {
		return "working";
	}

}
