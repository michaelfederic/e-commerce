package com.example.ecommerceapp.paypal.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayPalOrderDTO {
	@NotNull(message="{paypal.intent.null}")
	private String intent;
	
	@NotNull(message="{paypal.payer.null}")
	private PayPalPayeeDTO payer;

    @NotNull(message="{paypal.purchaseUnits.null}")
	private List<PayPalPurchaseUnitDTO> purchaseUnits;
    
    @NotNull(message="{paypal.purchaseUnits.null}")
	private PayPalApplicationContextDTO applicationContext;
	
}
