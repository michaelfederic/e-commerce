package com.example.ecommerceapp.exception;

public class OrderDetailsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public OrderDetailsException(String message) {
        super(message);
    }
}
