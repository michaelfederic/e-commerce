package com.example.ecommerceapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerceapp.entity.Customer;
import com.example.ecommerceapp.exception.CustomerException;
import com.example.ecommerceapp.model.CustomerDTO;
import com.example.ecommerceapp.model.RegisterCustomerDTO;
import com.example.ecommerceapp.model.ResponseMessage;
import com.example.ecommerceapp.repository.CustomerRepository;
import com.example.ecommerceapp.security.JwtTokenService;

@Service
@Transactional
public class EcommerceServiceImpl implements EcommerceService{
	@Autowired
	AuthenticationProvider authenticationProvider;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	JwtTokenService jwtTokenService;
	/**
	 * Authenticates a customer using the provided credentials and generates a JWT token for them.
	 *
	 * @param customerDTO the DTO representing the customer's login details
	 * @return a ResponseMessage containing the JWT token if authentication is successful
	 */
	@Override
	public ResponseMessage authenticate(CustomerDTO customerDTO) {
	    // Authenticate the customer using the provided credentials
	    Authentication authentication = authenticationProvider.authenticate(
	            new UsernamePasswordAuthenticationToken(customerDTO.getUsername(), customerDTO.getPassword()));

	    // If authentication is successful, generate a JWT token for the customer
	    String jwtToken = jwtTokenService.generateToken(authentication);

	    // Return the JWT token in a ResponseMessage object
	    return ResponseMessage.createWithToken(jwtToken);
	}
	
	/**
	 * Register a customer 
	 * 
	 * @Param customerDTO the DTO for registering a new customer
	 * @return A response indicating the customer has been successfully registered
	 */
	@Override
	public ResponseMessage register(RegisterCustomerDTO customerDTO) {
		// Find the user with the specified username
		Optional<Customer> op = customerRepository
				.findByUsername(customerDTO.getUsername());
		
		// If there is a user in present, throw exception user already exists
		if(op.isPresent()) {
			throw new CustomerException("The username: \'" + customerDTO.getUsername() + "\' already exists.");
		}

		// If all is well, create the customer entity using dtoToentity converter
		Customer customer = RegisterCustomerDTO.dtoToEntity(customerDTO);
		
		// Store the newly formed customer in database
		customerRepository.save(customer);
		
		
		return ResponseMessage.createWithMessage("success");
	}
	
	
	
}
