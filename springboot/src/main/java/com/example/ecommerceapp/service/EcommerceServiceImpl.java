package com.example.ecommerceapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerceapp.entity.Customer;
import com.example.ecommerceapp.entity.CustomerCart;
import com.example.ecommerceapp.entity.Product;
import com.example.ecommerceapp.exception.CustomerException;
import com.example.ecommerceapp.exception.ResourceNotFoundException;
import com.example.ecommerceapp.model.CustomerCartDTO;
import com.example.ecommerceapp.model.CustomerDTO;
import com.example.ecommerceapp.model.ProductDTO;
import com.example.ecommerceapp.model.RegisterCustomerDTO;
import com.example.ecommerceapp.model.ResponseMessage;
import com.example.ecommerceapp.repository.CustomerRepository;
import com.example.ecommerceapp.repository.ProductRepository;
import com.example.ecommerceapp.security.JwtTokenService;

@Service
@Transactional
public class EcommerceServiceImpl implements EcommerceService{
	@Autowired
	AuthenticationProvider authenticationProvider;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
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

	@Override
	public CustomerDTO getDetails(String username) {
		// Get user from repository, throw exception if not found
		Customer customer = customerRepository
				.findByUsername(username)
				.orElseThrow(()-> new ResourceNotFoundException("Customer does not exist"));
		
		// Convert entity to dto then build instance of CustomerDTO class since it is in CustomerDTOBuilder format
		return CustomerDTO.entityToDto(customer).build();
	}
	
	@Override
	public CustomerCartDTO addShoppingCart(CustomerCartDTO customerCartDTO) {
		// Find the user with the specified id
		Optional<Customer> op = customerRepository
				.findById(customerCartDTO.getCustomerId());
		
		// If there is not a user in present, throw exception user already exists
		if(!op.isPresent()) {
			throw new ResourceNotFoundException("No user with id: \'" + customerCartDTO.getCustomerId() + "\' present");
		}
		
		// Get the customer
		Customer customer = op.get();
		
		/*
		 * (**Products are not from my database, they are obtained from an external api (https://fakestoreapi.com/)**)
		 */
		
		// Save products before building cart to ensure products exist
		this.saveProducts(customerCartDTO.getProductDTOs());
		
		
		// If customer has a cart already
		if(customer.getCustomerCart()!=null) {
			List<Product> products = customerCartDTO
					.getProductDTOs()
					.stream()
					.map(pruductDto-> Product.dtoToEntityBuilder(pruductDto).build())
					.toList();
			
			// update existing cart with products
			customer.getCustomerCart().setProducts(products);
			
			// Set date updated
			customer.getCustomerCart().setDateUpdated(LocalDateTime.now());
			
			
		}
		// Create a new cart with products
		else {
			
			
			//Build customerCart from CustomerCartBuilder
			CustomerCart customerCart = CustomerCart.dtoToEntityBuilder(customerCartDTO).build();
			
			// Set the current date of cart creation
			customerCart.setDateCreated(LocalDateTime.now());
			customerCartDTO.setDateCreated(LocalDateTime.now());
			
			// Set the customers cart
			customer.setCustomerCart(customerCart);
		}
		
		// Set the dto's date creation and update
		customerCartDTO.setDateCreated(customer.getCustomerCart().getDateCreated());
		customerCartDTO.setDateUpdated(LocalDateTime.now());
		
		return customerCartDTO;
		
	}
	
	public void saveProducts(List<ProductDTO> productDTOs) {
		
	    productDTOs.forEach(productDTO -> {
	        // Check if the product already exists in the database
	        Optional<Product> existingProduct = productRepository.findById(productDTO.getId());

	        // If the product already exists, skip it
	        if (existingProduct.isPresent()) {
	            return;
	        }

	        // Convert ProductDTO to Product entity using DTOToEntityBuilder in the entity class
	        Product product = Product.dtoToEntityBuilder(productDTO).build();

	        // Save the Product entity to the database
	        productRepository.save(product);
	    });
	}

	
	
	
	
	
	
	
}
