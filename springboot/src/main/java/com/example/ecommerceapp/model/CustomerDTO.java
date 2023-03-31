package com.example.ecommerceapp.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.example.ecommerceapp.entity.Customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
	private Integer customerId;
	@NotEmpty(message="{customer.username.null}")
	@Pattern(regexp="^[_A-Za-z0-9][A-Za-z0-9]{0,19}$", message="{customer.username.invalid}")
	private String username;
	
	@Email(message="{customer.email.invalid}")
	private String email;
	
	@NotEmpty(message="{customer.password.null}")
	private String password;
	
	private CustomerCartDTO customerCartDTO;
	
	private List<OrderDetailsDTO> orderDetailsDTOs;
	
    private List<GrantedAuthority> authorities;
    
    public static CustomerDTOBuilder entityToDto(Customer customer) {
        CustomerCartDTO customerCartDTO = null;
        if (customer.getCustomerCart() != null) {
            customerCartDTO = CustomerCartDTO.entityToDto(customer.getCustomerCart()).build();
        }
        
        List<OrderDetailsDTO> orderDetailsDTOs = null;
        
        if (customer.getOrders() != null) {
            orderDetailsDTOs = customer.getOrders()
                    .stream()
                    .map(o-> OrderDetailsDTO.entityToDto(o).build())
                    .toList();
        }
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .password(null)
                .customerCartDTO(customerCartDTO)
                .orderDetailsDTOs(orderDetailsDTOs)
                .authorities(null);
    }
	
}
