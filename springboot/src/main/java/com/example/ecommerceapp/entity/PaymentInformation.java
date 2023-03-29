package com.example.ecommerceapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class PaymentInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentId;
	private String nameoOnCard;
	private Long creditCardNumber;
	private String expirationDate;
	private Short CVV;
	@Enumerated(EnumType.STRING)
	private CreditCardType paymentType;
	@OneToOne(mappedBy="paymentInformation")
    private OrderDetails order;
	
}

enum CreditCardType{
	DEBIT_CARD, CREDIT_CARD, PAYPAL
}
