package com.example.ecommerceapp.paypal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String country_code;
    private String address_line_1;
    private String admin_area_2;
    private String admin_area_1;
    private String postal_code;
    
}
