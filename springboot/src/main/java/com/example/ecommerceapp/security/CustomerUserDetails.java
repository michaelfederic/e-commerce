package com.example.ecommerceapp.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.ecommerceapp.entity.Customer;
import com.example.ecommerceapp.exception.ResourceNotFoundException;
import com.example.ecommerceapp.repository.CustomerRepository;

//load user from the database and return user details
public class CustomerUserDetails implements UserDetailsService{
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> customer = customerRepository.findByUsername(username);
		
		if(!customer.isPresent()) {
			throw new ResourceNotFoundException("Customer with username: \""+username+"\" does not exist");
		}
		
		return new Customer(
				customer.get().getUsername(), 
				customer.get().getPassword(),
				(List<GrantedAuthority>) customer.get().getAuthorities()
				);
	}

}
