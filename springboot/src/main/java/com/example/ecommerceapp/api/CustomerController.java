package com.example.ecommerceapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerceapp.model.CustomerCartDTO;
import com.example.ecommerceapp.model.CustomerDTO;
import com.example.ecommerceapp.service.EcommerceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@Validated
public class CustomerController {
	
	@Autowired
	EcommerceService ecommerceService;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello Message";
	}
	
	@GetMapping("/details")
	public ResponseEntity<CustomerDTO> customerDetails() {
		//extract the username from security context which comes from the jwt token
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok().body(ecommerceService.getDetails(username));
		
		
	}
	
	@PutMapping("/shoppingcart")
	public ResponseEntity<CustomerCartDTO> addShoppingCart(@Valid @RequestBody CustomerCartDTO customerCartDTO){
		return ResponseEntity.ok().body(ecommerceService.addShoppingCart(customerCartDTO));
	}
}
