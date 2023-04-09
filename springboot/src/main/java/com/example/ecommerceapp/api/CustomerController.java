package com.example.ecommerceapp.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerceapp.model.CustomerDTO;
import com.example.ecommerceapp.model.OrderDTO;
import com.example.ecommerceapp.paypal.model.PayPalCreateOrderResponseDTO;
import com.example.ecommerceapp.paypal.service.PayPalService;
import com.example.ecommerceapp.service.EcommerceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@Validated
public class CustomerController {
	
	@Autowired
	EcommerceService ecommerceService;
	
	@Autowired
	PayPalService paypalService;
	
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
	
	@GetMapping("/orderdetails/{orderId}")
	public ResponseEntity<PayPalCreateOrderResponseDTO> getOrderdetails(@PathVariable String orderId) {
		return ResponseEntity.ok().body(paypalService.getOrderDetails(orderId));
	}
	
	@GetMapping("/getmyorders")
	public ResponseEntity<List<OrderDTO>> getOrders(){
		return ResponseEntity.ok().body(ecommerceService.getOrders());
	}
	
}
