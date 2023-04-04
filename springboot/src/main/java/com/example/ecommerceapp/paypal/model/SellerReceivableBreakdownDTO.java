package com.example.ecommerceapp.paypal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerReceivableBreakdownDTO {
    private AmountDTO gross_amount;
    private AmountDTO paypal_fee;
    private AmountDTO net_amount;
}