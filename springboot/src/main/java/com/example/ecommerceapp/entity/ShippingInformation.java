package com.example.ecommerceapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="shipping_information")
public class ShippingInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shippingId;
	private String firstName;
	private String lastName;
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
