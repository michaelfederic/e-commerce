package com.example.ecommerceapp.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class CustomerController {
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello Message";
	}
}
