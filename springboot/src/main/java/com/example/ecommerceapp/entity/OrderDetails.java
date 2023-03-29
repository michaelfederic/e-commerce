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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
    private String cartId;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    
    @ManyToMany
    @JoinTable(
    		name="order_product",
    		joinColumns = @JoinColumn(name="order_id"),
    		inverseJoinColumns = @JoinColumn(name="product_id")
    		)
    private List<Product> products;
    private Integer totalCost;
    
    @OneToMany(mappedBy="order")
    private List<ShippingInformation> shippingInformation;
    
    @OneToOne
    @JoinColumn(name="payment_id")
    private PaymentInformation paymentInformation;
    private OrderStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    
}

enum OrderStatus {
	ACTIVE, COMPLETED
}
