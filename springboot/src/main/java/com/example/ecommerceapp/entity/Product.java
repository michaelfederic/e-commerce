package com.example.ecommerceapp.entity;

import java.util.List;

import com.example.ecommerceapp.entity.Product.ProductBuilder;
import com.example.ecommerceapp.model.ProductDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    private String title;
    private Float price;
    private String category;
    private String description;
    private String image;
    private String quantity;
    @ManyToMany(mappedBy="products")
    private List<OrderDetails> orders;
    
    @ManyToMany(mappedBy="products")
    private List<CustomerCart> carts;
    
    
    public static ProductBuilder dtoToEntityBuilder(ProductDTO productDto) {
    	return  Product.builder()
    			.id(productDto.getId())
    			.title(productDto.getTitle())
    			.price(productDto.getPrice())
    			.category(productDto.getCategory())
    			.description(productDto.getDescription())
    			.image(productDto.getImage())
    			.quantity(productDto.getQuantity());
    }
}
