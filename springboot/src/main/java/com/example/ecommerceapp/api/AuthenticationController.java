package com.example.ecommerceapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerceapp.model.CustomerDTO;
import com.example.ecommerceapp.model.RegisterCustomerDTO;
import com.example.ecommerceapp.model.ResponseMessage;
import com.example.ecommerceapp.service.EcommerceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
//enable method-level validation
@Validated
public class AuthenticationController {
	@Autowired
	EcommerceService ecommerceService;
	
	
	@PostMapping("/auth/login")
	public ResponseEntity<ResponseMessage> login(@RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<>(ecommerceService.authenticate(customerDTO),HttpStatus.OK);
		
	}
	
	@PostMapping("/auth/register")
	public ResponseEntity<ResponseMessage> register(@Valid @RequestBody RegisterCustomerDTO customerDTO){
		return new ResponseEntity<>(ecommerceService.register(customerDTO), HttpStatus.CREATED);
	}
	
	
}


