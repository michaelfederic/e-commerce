package com.example.ecommerceapp.model;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ecommerceapp.entity.CustomerCart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCartDTO {
    
    // The unique identifier for the cart
	@NotNull(message="{customer.cart.cartId.null}")
	private Integer cartId;
    
    // The unique identifier for the customer
	@NotNull(message="{customer.cart.customer.null}")
    private Integer customer;
    
    // The list of products in the cart, represented as a list of ProductDTO objects
    @NotNull(message="{customer.cart.productDTOs.null}")
    private List<ProductDTO> productDTOs;
    
    // The total cost of all the products in the cart
    private Integer totalCost;
    
    // The date the cart was created
    private LocalDateTime dateCreated;
    
    // The date the cart was last updated
    private LocalDateTime dateUpdated;
    
    /**
     * A method that converts a CustomerCart entity object to a CustomerCartDTO object
     * 
     * @param customerCart The CustomerCart entity object to be converted
     * @return A CustomerCartDTO object representing the same data as the entity object
     */
    public static CustomerCartDTOBuilder entityToDto(CustomerCart customerCart) {
    	
        // Convert the list of Product objects in the entity object to a list of ProductDTO objects
    	List<ProductDTO> products = customerCart
    								.getProducts()
    								.stream()
    								.map(product-> ProductDTO.entityToDto(product).build())
    								.toList();
    	
        // Build and return a new CustomerCartDTO object using the data from the entity object
    	return CustomerCartDTO.builder()
    			.cartId(customerCart.getCartId())
    			.customer(customerCart.getCustomer().getCustomerId())
    			.productDTOs(products)
    			.totalCost(customerCart.getTotalCost())
    			.dateCreated(customerCart.getDateCreated())
    			.dateUpdated(customerCart.getDateUpdated());
    }			
}

	