package com.example.ecommerceapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ecommerceapp.entity.Product;

public interface ProductRepository extends CrudRepository<Product,Integer>{

}
