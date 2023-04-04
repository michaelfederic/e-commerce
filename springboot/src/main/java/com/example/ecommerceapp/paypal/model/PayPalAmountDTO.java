package com.example.ecommerceapp.paypal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayPalAmountDTO {
    private String currency_code;
    private String value;
    private PayPalItemTotalDTO breakdown;
}
