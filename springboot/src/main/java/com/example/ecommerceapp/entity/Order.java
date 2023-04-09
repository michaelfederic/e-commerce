package com.example.ecommerceapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ecommerceapp.model.OrderDTO;
import com.example.ecommerceapp.model.OrderDTO.OrderDTOBuilder;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="order_details")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
    
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    private String paypalOrderId;
    
    
}

