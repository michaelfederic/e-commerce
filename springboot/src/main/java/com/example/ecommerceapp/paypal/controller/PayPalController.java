package com.example.ecommerceapp.paypal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerceapp.model.OrderDTO;
import com.example.ecommerceapp.model.ResponseMessage;
import com.example.ecommerceapp.paypal.model.OrderResponse;
import com.example.ecommerceapp.paypal.model.PayPalCreateOrderRequestDTO;
import com.example.ecommerceapp.paypal.model.PayPalCreateOrderResponseDTO;
import com.example.ecommerceapp.paypal.service.PayPalService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Data;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class PayPalController {
	
	@Autowired
	PayPalService paypalService;
	
	@GetMapping("/paypal/generatetoken")
	public ResponseMessage generateToken() {
		return ResponseMessage.createWithMessage(paypalService.generateAccessToken());
	}
	
	// For testing my order to paypal order method
	@GetMapping("/getorder")
	public ResponseEntity<PayPalCreateOrderRequestDTO> getOrder(@RequestBody OrderDTO orderDTO) {
		PayPalCreateOrderRequestDTO order = paypalService.getOrderRequest(orderDTO);
		return ResponseEntity.ok().body(order);
	}
	
	
	@PostMapping("/checkout")
	public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderDTO orderDTO) {
		OrderResponse order = paypalService.placeOrder(orderDTO);
		return ResponseEntity.ok().body(order);
	}
	
	@PostMapping("/capture")
	public ResponseEntity<PayPalCreateOrderResponseDTO> getCapture(@RequestBody OrderRequest orderId) {
		System.out.println("OrderId from frontend: "+orderId.getOrderID());
		PayPalCreateOrderResponseDTO orderResponse = paypalService.capturePayment(orderId.getOrderID());
		
		return ResponseEntity.ok().body(orderResponse);
	}
}

@Data
class OrderRequest{
	private String orderID;
}
