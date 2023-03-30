package com.example.ecommerceapp.model;

import java.util.List;

import com.example.ecommerceapp.entity.Product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
	
	@NotNull(message="{product.id.null}")
	private Integer id;
	@NotNull(message="{product.title.null}")
    private String title;
	@NotNull(message="{product.price.null}")
    private Integer price;
	@NotNull(message="{product.category.null}")
    private String category;
	@NotNull(message="{product.description.null}")
    private String description;
	@NotNull(message="{product.image.null}")
    private String image;
	
	//convert from entity to dto
    public static ProductDTOBuilder entityToDto(Product product) {
    	
    	return ProductDTO.builder()
    			.id(product.getId())
    			.title(product.getTitle())
    			.price(product.getPrice())
    			.category(product.getCategory())
    			.description(product.getDescription())
    			.image(product.getImage());
    			
    }
}
