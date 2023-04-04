package com.example.ecommerceapp.paypal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayerDTO {
    private NameDTO name;
    private String email_address;
    private String payer_id;
    private AddressDTO address;
}