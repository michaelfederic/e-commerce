package com.example.ecommerceapp.model;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
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
	
	@NotEmpty(message="{product.title.null}")
    private String title;
	
	@NotNull(message="{product.price.null}")
    private Float price;
	
	@NotEmpty(message="{product.category.null}")
    private String category;
	
	@NotEmpty(message="{product.description.null}")
    private String description;
	
	@NotEmpty(message="{product.image.null}")
    private String image;
	
	@NotEmpty(message="{product.quantity.null}")
	private String quantity;
	
}
