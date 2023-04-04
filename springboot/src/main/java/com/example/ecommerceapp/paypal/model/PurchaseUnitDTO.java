package com.example.ecommerceapp.paypal.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseUnitDTO {
    private String reference_id;
    private AmountDTO amount;
    private PayeeDTO payee;
    private String description;
    private List<ItemDTO> items;
    private ShippingDTO shipping;
    private PaymentsDTO payments;
}