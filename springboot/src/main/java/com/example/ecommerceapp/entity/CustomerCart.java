package com.example.ecommerceapp.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ecommerceapp.model.CustomerCartDTO;
import com.example.ecommerceapp.repository.CustomerRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="customer_cart")
public class CustomerCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;
	
	@OneToOne(mappedBy="customerCart")
    private Customer customer;
    
    @ManyToMany
    @JoinTable(
    		name="cart_product",
			joinColumns = @JoinColumn(name="cart_id"),
    		inverseJoinColumns = @JoinColumn(name="product_id")
    		)
    private List<Product> products;
    private Integer totalCost;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    
    public static CustomerCartBuilder dtoToEntityBuilder(CustomerCartDTO customerCart) {
    	
        // Convert the list of ProductDTO objects in the entity object to a list of Product objects
    	List<Product> products = customerCart
				.getProductDTOs()
				.stream()
				.map(product-> Product.dtoToEntityBuilder(product).build())
				.toList();
    	
        // Build and return a new CustomerCartBuilder object using the data from the entity object
    	return CustomerCart.builder()
    			.cartId(customerCart.getCartId())
    			.products(products)
    			.totalCost(customerCart.getTotalCost())
    			.dateCreated(customerCart.getDateCreated())
    			.dateUpdated(customerCart.getDateUpdated());
    }			
}

