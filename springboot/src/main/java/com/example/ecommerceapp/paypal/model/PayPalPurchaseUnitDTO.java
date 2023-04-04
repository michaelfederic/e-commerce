package com.example.ecommerceapp.paypal.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayPalPurchaseUnitDTO {
    private List<PayPalItemDTO> items;
    private PayPalAmountDTO amount;
    private PayPalPayeeDTO payee;
}
