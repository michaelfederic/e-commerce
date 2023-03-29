package com.example.ecommerceapp.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String title;
    private Integer price;
    private String category;
    private String description;
    private String image;
    @ManyToMany(mappedBy="products")
    private List<OrderDetails> orders;
    
    @ManyToMany(mappedBy="products")
    private List<CustomerCart> carts;
    
}
