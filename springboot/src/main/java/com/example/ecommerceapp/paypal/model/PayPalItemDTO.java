package com.example.ecommerceapp.paypal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayPalItemDTO {
    private String name;
    private String description;
    private String quantity;
    private String image;
    private PayPalUnitAmountDTO unit_amount;
}
