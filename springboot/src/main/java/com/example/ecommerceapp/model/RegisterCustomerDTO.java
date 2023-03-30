package com.example.ecommerceapp.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.ecommerceapp.entity.Customer;
import com.example.ecommerceapp.security.CustomAuthority;

import jakarta.validation.constraints.AssertTrue;
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
public class RegisterCustomerDTO {
	//minimum 1 character , max 20 characters
	@NotEmpty(message="{customer.username.null}")
	@Pattern(regexp="^[_A-Za-z0-9][A-Za-z0-9]{0,19}$", message="{customer.username.invalid}")
	private String username;
	
	@Email(message="{customer.email.invalid}")
	private String email;
	
	@NotEmpty(message="{customer.password.null}")
	private String password;
	
	@NotEmpty(message="{customer.confirm.password.null}")
	private String confirmPassword;
	
	/**
	 * 
	 * @param customerDTO for registering customer provided details
	 * @return A customer entity with password encrypted and roles set
	 */
	
	 public static Customer dtoToEntity(RegisterCustomerDTO customerDTO) {
		 	List<GrantedAuthority> authorities = new ArrayList<>();
	        authorities.add(new CustomAuthority("ROLE_USER"));
	        return Customer.builder()
	                .username(customerDTO.getUsername())
	                .email(customerDTO.getEmail())
	                .password(new BCryptPasswordEncoder().encode(customerDTO.getPassword()))
	                .authorities(authorities)
	                .build();
	    }
	 
	 @AssertTrue(message="{customer.password.mismatch}")
	 private boolean isPasswordMatch() {
		 return password != null && password.equals(confirmPassword);
	 }
}
