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
public class SellerProtectionDTO {
    private String status;
    private List<String> dispute_categories;
}