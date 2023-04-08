package com.example.ecommerceapp.paypal.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.example.ecommerceapp.model.OrderDTO;
import com.example.ecommerceapp.paypal.model.PayPalAmountDTO.PayPalAmountDTOBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayPalCreateOrderRequestDTO {

	private String intent = "CAPTURE";
    private List<PayPalPurchaseUnitDTO> purchase_units;
    private PayPalApplicationContextDTO application_context;

    @JsonIgnore
    private String returnUrl = "http://localhost:4200/OneStopShop";
    

    @JsonIgnore
	private String cancelUrl = "http://localhost:4200/checkout";
    

    @JsonIgnore
    private String currency_code = "USD";
    

    @JsonIgnore
    private String payeeEmailAddress = "sb-ok43fj25382721@business.example.com";
    
   // Converts my order details object to a suitable paypal order
  	public PayPalCreateOrderRequestDTO myOrderToPaypalOrder(OrderDTO orderDTO) {
  		
  		//unit amount total cost
  		PayPalUnitAmountDTO payPalUnitAmountDTO = PayPalUnitAmountDTO.builder()
					.currency_code(currency_code)
					.value(String.valueOf(orderDTO.getTotalCost()))
					.build();
				
  		
  		// Items
  		List<PayPalItemDTO> items = orderDTO.getProductDTOs()
  				.stream()
  				.map(productDTO -> PayPalItemDTO.builder()
  									.name(productDTO.getTitle())
  									.description(productDTO.getDescription())
  									.quantity(productDTO.getQuantity())
  									.image(productDTO.getImage())
  									.unit_amount(
  											PayPalUnitAmountDTO.builder()
  											.currency_code(currency_code)
  											.value(String.valueOf(productDTO.getPrice()))
  											.build())
  									.build())
  				.toList();
  								
  		// Amount
  		PayPalAmountDTOBuilder amount = PayPalAmountDTO.builder()
  									.currency_code(currency_code)
  									.value(String.valueOf(orderDTO.getTotalCost()))
  									.breakdown(PayPalItemTotalDTO.builder()
  												.item_total(payPalUnitAmountDTO)
  												.items(items)
  												.build());
  									
  		// Payee
  		PayPalPayeeDTO payee = PayPalPayeeDTO.builder()
  								.email_address(payeeEmailAddress)
  								.build();
  		
  		
  		// Application context
  		application_context = PayPalApplicationContextDTO.builder()
  				.return_url(returnUrl)
  				.cancel_url(cancelUrl)
  				.build();
  		
  		purchase_units = new ArrayList<>();
  		
  		// Purchase unit
 		PayPalPurchaseUnitDTO purchaseUnit = PayPalPurchaseUnitDTO.builder()
					 				.items(items)
					 				.amount(amount.build())
					 				.payee(payee)
					 				.build();
 		purchase_units.add(purchaseUnit);
 		
 		
  		
  		return PayPalCreateOrderRequestDTO.builder()
  				.intent(intent)
  				.purchase_units(purchase_units)
  				.application_context(application_context)
  				.build();
  				
  	}
}
