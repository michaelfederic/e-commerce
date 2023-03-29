package com.example.ecommerceapp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Customer {
	private Integer customerId;
	private String username;
	private String password;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="customer_cart_id")
	private CustomerCart customerCart;
	@OneToMany(mappedBy="customer")
	private List<OrderDetails> orders;
	
	
}
