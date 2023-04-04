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
public class PayPalCreateOrderResponseDTO {
    private String id;
    private String intent;
    private String status;
    private PaymentSourceDTO payment_source;
    private List<PurchaseUnitDTO> purchase_units;
    private PayerDTO payer;
    private String create_time;
    private String update_time;
    private List<LinkDTO> links;
}