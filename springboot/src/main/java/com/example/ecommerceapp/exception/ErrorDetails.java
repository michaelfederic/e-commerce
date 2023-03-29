package com.example.ecommerceapp.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
	private String errorMessage;
	private Integer statusCode;
	private LocalDateTime dateOccured;
	
}
