package com.example.ecommerceapp.paypal.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerceapp.model.OrderDetailsDTO;
import com.example.ecommerceapp.paypal.model.OrderResponse;
import com.example.ecommerceapp.paypal.model.PayPalCreateOrderRequestDTO;
import com.example.ecommerceapp.paypal.model.PayPalCreateOrderResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class PayPalService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${paypal.client.id}")
	private String clientId;
	
	@Value("${paypal.client.secret}")
	private String clientSecret;
	
	public String generateAccessToken() {
	
		// Create a new HttpHeaders instance and set the Authorization header with the client ID and secret
		HttpHeaders headers = new HttpHeaders();
		
		String authHeader = clientId+":"+clientSecret;

		String authHeaderValue = "Basic " +  Base64.getEncoder().encodeToString(authHeader.getBytes(StandardCharsets.UTF_8));
		headers.set("Authorization", authHeaderValue);
		
		// Create a new httpEntity with the headers
		HttpEntity<String> entity = new HttpEntity<>("grant_type=client_credentials", headers);
		
		// Send a POST request to the PayPal authorization server to exchange the client ID and secret for an access token
		ResponseEntity<String> response = restTemplate
				.exchange("https://api-m.sandbox.paypal.com/v1/oauth2/token", 
						HttpMethod.POST, 
						entity, 
						String.class);
		
		// Parse the JSON response body to extract the access token
		JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
		String accessToken = jsonObject.get("access_token").getAsString();
		
		// print the access token to the console
//		System.out.println("Access token: " + accessToken);
		
		return accessToken;
		

	}
	
	
	// Create paypal order from order details
	public OrderResponse placeOrder(OrderDetailsDTO orderDetailsDTO) {
		
		PayPalCreateOrderRequestDTO order = new PayPalCreateOrderRequestDTO();
		
		order = order.myOrderToPaypalOrder(orderDetailsDTO);
		
		// Create header to set authorization header
		HttpHeaders headers = new HttpHeaders();
		
		// Obtain the access token from paypal
		String accessToken = generateAccessToken();
		
		// 
		String requestId = UUID.randomUUID().toString();
		
		headers.set("Authorization", "Bearer "+ accessToken);
		headers.set("PayPal-Request-Id", requestId);
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		// Create entity with headers to order details
		HttpEntity<PayPalCreateOrderRequestDTO> requestEntity = new HttpEntity<>(order, headers);
	    
		//Send request to PayPal API to create the order and send back an order id
	    ResponseEntity<PayPalCreateOrderResponseDTO> responseEntity = restTemplate
	    		.postForEntity("https://api-m.sandbox.paypal.com/v2/checkout/orders", 
	    				requestEntity, 
	    				PayPalCreateOrderResponseDTO.class);
	    
	    PayPalCreateOrderResponseDTO responseDTO = responseEntity.getBody();
	    
	    // If the response code is 201 created
	    if(responseEntity.getStatusCode()==HttpStatus.CREATED) {
	    	System.out.println("Order id: "+responseDTO.getId());
	    	
	    	return new OrderResponse(responseDTO.getId());
	    }
	    
	    return null;
	    
	   
	    		
	}
	
	// Capture the payment for an order
	public PayPalCreateOrderResponseDTO capturePayment(String orderId){
		// Obtain the access token from paypal
		String accessToken = generateAccessToken();
		String paypalUrl = "https://api-m.sandbox.paypal.com/v2/checkout/orders/"+orderId+"/capture";
		
		String ordId = "";
		String status  ="";
		
		
		HttpHeaders headers = new HttpHeaders();
		String requestId = UUID.randomUUID().toString();
		headers.set("PayPal-Request-Id", requestId);
		headers.set("Authorization", "Bearer "+accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		
		 try {
		// Using exchange method to add additional headers to request
		ResponseEntity<PayPalCreateOrderResponseDTO> responseEntity = restTemplate
				.exchange(paypalUrl, HttpMethod.POST,requestEntity, PayPalCreateOrderResponseDTO.class);
		
		//Parse the JSON response into a java object 
		ObjectMapper objectMapper = new ObjectMapper();
		
		//
		String jsonString = objectMapper.writeValueAsString(responseEntity.getBody());
		
		JsonObject jsonResponse = JsonParser.parseString(jsonString).getAsJsonObject();
		ordId = jsonResponse.get("id").getAsString();
		status = jsonResponse.get("status").getAsString();
		
		System.out.println("Order Id from capture: "+ ordId);
		System.out.println("Order status from capture: "+ status);
		 }catch(JsonProcessingException e) {
			 System.err.println("Error processing JSON: " + e.getMessage());
		 }
		return PayPalCreateOrderResponseDTO.builder()
				.id(ordId)
				.status(status)
				.build();
		
	} 
	
	// For testing if my dto conversion works that is matches PayPal's Json object
	public PayPalCreateOrderRequestDTO getOrderRequest(OrderDetailsDTO orderDetailsDTO) {
		PayPalCreateOrderRequestDTO order = new PayPalCreateOrderRequestDTO();
		return order.myOrderToPaypalOrder(orderDetailsDTO);
	}
	
}


