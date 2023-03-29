package com.example.ecommerceapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CustomerCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String cartId;
	
	@OneToOne(mappedBy="customerCart")
    private Customer customer;
    
    @ManyToMany
    @JoinTable(
    		name="cart_product",
			joinColumns = @JoinColumn(name="cart_id"),
    		inverseJoinColumns = @JoinColumn(name="product_id")
    		)
    private List<Product> products;
    private Integer totalCost;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;	
}

