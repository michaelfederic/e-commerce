package com.example.ecommerceapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.ecommerceapp.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
	Optional<Customer> findByUsername(String username);
	Optional<Customer> findByEmail(String email);
}
