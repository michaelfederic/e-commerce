package com.example.ecommerceapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ecommerceapp.entity.CustomerCart;

public interface CustomerCartRepository extends CrudRepository<CustomerCart,Integer>{

}