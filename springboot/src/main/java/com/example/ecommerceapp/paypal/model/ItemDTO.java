package com.example.ecommerceapp.paypal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private String name;
    private AmountDTO unit_amount;
    private AmountDTO tax;
    private String quantity;
    private String description;
}