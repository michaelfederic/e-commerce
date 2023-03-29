package com.example.ecommerceapp.exception;

import jakarta.servlet.ServletException;

public class NoHandlerFoundException extends ServletException {

    private static final long serialVersionUID = 1L;

    public NoHandlerFoundException(String message) {
        super(message);
    }
}
