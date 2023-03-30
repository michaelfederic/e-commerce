package com.example.ecommerceapp.model;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ecommerceapp.entity.CreditCardType;
import com.example.ecommerceapp.entity.OrderDetails;
import com.example.ecommerceapp.entity.PaymentInformation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInformationDTO {
	
	private Integer paymentId;

	@NotNull(message="{payment.nameOnCard.null}")
	private String nameOnCard;

	@NotNull(message="{payment.creditCardNumber.null}")
	private Long creditCardNumber;

	@NotNull(message="{payment.expirationDate.null}")
	private String expirationDate;

	@NotNull(message="{payment.cvv.null}")
	private Short cvv;

	@NotNull(message="{payment.paymentType.null}")
	private CreditCardType paymentType;

	
	private OrderDetailsDTO orderDTO;
	
	//convert entity to a dto class
	public static PaymentInformationDTOBuilder entityToDto(PaymentInformation payment) {
        return PaymentInformationDTO.builder()
                .paymentId(payment.getPaymentId())
                .nameOnCard(payment.getNameOnCard())
                .creditCardNumber(payment.getCreditCardNumber())
                .expirationDate(payment.getExpirationDate())
                .cvv(payment.getCvv())
                .paymentType(payment.getPaymentType())
                .orderDTO(OrderDetailsDTO.entityToDto(payment.getOrder()).build());
    }
}
