package com.example.ecommerceapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ecommerceapp.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
