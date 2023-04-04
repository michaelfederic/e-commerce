package com.example.ecommerceapp.paypal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreakdownDTO {
    private AmountDTO item_total;
    private AmountDTO shipping;
    private AmountDTO handling;
    private AmountDTO insurance;
    private AmountDTO shipping_discount;
}