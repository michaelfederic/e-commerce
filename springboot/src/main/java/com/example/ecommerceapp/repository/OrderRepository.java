package com.example.ecommerceapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.ecommerceapp.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	Optional<Order> findByPaypalOrderId(String paypalOrderId);
}
