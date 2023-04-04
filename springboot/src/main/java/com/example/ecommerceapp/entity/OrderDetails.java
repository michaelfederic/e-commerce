package com.example.ecommerceapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_details")
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
    
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
    private Float totalCost;
    
    @OneToMany(mappedBy="order")
    private List<ShippingInformation> shippingInformation;
    
    @OneToOne
    @JoinColumn(name="payment_id")
    private PaymentInformation paymentInformation;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    
}

