package com.example.ecommerceapp.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {
	
	Environment environment;
	
	@Autowired
	public ExceptionController(Environment environment) {
		this.environment = environment;
	}
	
	// handle all e-commerce exceptions except 404 errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exception) {
        HttpStatus status = getStatusFromException(exception);
        ErrorDetails errorDetails = new ErrorDetails(
            exception.getMessage(),
            status.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDetails, status);
    }

    /* 
     * handle e-commerce 404 errors and other instances were the server is
     * unable to find a suitable handler for a specific request. 
    */
    @ExceptionHandler({ResourceNotFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<ErrorDetails> handle404Exception(Exception exception) {
        ErrorDetails errorDetails = new ErrorDetails(
            exception.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    //get the status code from the exception
    private HttpStatus getStatusFromException(Exception exception) {
        if (exception instanceof CustomerException ||
            exception instanceof OrderDetailsException ||
            exception instanceof PaymentInfoException ||
            exception instanceof ShippingInfoException ||
            exception instanceof ProductException) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    
 // Handles validation exceptions for MethodArgumentNotValidException and ConstraintViolationException
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorDetails> handleValidationException(Exception exception) {
        
        String errorMessage = "";

        // If the exception is MethodArgumentNotValidException
        if(exception instanceof MethodArgumentNotValidException) {
            errorMessage = getMethodArgumentNotValidErrorMessage((MethodArgumentNotValidException) exception);
        } 
        // If the exception is ConstraintViolationException
        else if(exception instanceof ConstraintViolationException) {
            errorMessage = getConstraintViolationErrorMessage((ConstraintViolationException) exception);
        } 
        // If the exception is neither of the above
        else {
            // handle other exceptions
        }

        // Create an ErrorDetails object with the error message and status code
        ErrorDetails errorDetails = new ErrorDetails(errorMessage, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        
        // Return a bad request response with the ErrorDetails object in the body
        return ResponseEntity.badRequest().body(errorDetails);
    }

    // Method to get error message for MethodArgumentNotValidException
    private String getMethodArgumentNotValidErrorMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }

    // Method to get error message for ConstraintViolationException
    private String getConstraintViolationErrorMessage(ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
    }

}
