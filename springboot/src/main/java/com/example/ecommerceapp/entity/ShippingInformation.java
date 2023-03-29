package com.example.ecommerceapp.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ShippingInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String shippingId;
	
	private String recipientName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String email;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDetails order;
}