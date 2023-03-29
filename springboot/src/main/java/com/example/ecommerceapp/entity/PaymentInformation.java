package com.example.ecommerceapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="payment_information")
public class PaymentInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentId;
	private String nameOnCard;
	private Long creditCardNumber;
	private String expirationDate;
	private Short cvv;
	@Enumerated(EnumType.STRING)
	private CreditCardType paymentType;
	@OneToOne(mappedBy="paymentInformation")
    private OrderDetails order;
	
}

enum CreditCardType{
	DEBIT_CARD, CREDIT_CARD, PAYPAL
}
