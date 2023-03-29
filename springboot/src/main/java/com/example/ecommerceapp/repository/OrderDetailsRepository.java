package com.example.ecommerceapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ecommerceapp.entity.OrderDetails;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Integer> {

}
