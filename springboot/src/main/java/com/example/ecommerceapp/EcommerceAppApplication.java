package com.example.ecommerceapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.ecommerceapp.entity.Customer;
import com.example.ecommerceapp.paypal.model.PayPalCreateOrderRequestDTO;
import com.example.ecommerceapp.repository.CustomerRepository;
import com.example.ecommerceapp.security.CustomAuthority;

@SpringBootApplication
public class EcommerceAppApplication implements CommandLineRunner {
	
	@Autowired
	CustomerRepository customerRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		customerRepository.deleteAll();
		 Customer user = new Customer();
	        user.setUsername("john.doe");
	        user.setPassword(new BCryptPasswordEncoder().encode("password"));
	        List<GrantedAuthority> authorities = new ArrayList<>();
	        authorities.add(new CustomAuthority("ROLE_USER"));
	        user.setAuthorities(authorities);
	        customerRepository.save(user);
		
	}
	
}
