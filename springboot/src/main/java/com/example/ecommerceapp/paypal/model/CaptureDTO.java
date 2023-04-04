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
public class CaptureDTO {
    private String id;
    private String status;
    private AmountDTO amount;
    private boolean final_capture;
    private SellerProtectionDTO seller_protection;
    private SellerReceivableBreakdownDTO seller_receivable_breakdown;
    private List<LinkDTO> links;
    private String create_time;
    private String update_time;
}