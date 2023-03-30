package com.example.ecommerceapp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage{
	private String token;
	private String message;
	
	public static ResponseMessage createWithToken(String token) {
		  ResponseMessage responseMessage = new ResponseMessage();
		  responseMessage.setToken(token);
        return responseMessage;
	}
	
	public static ResponseMessage createWithMessage(String message) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage(message);
      return responseMessage;
	}
}

 