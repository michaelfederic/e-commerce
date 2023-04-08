package com.example.ecommerceapp.model;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ecommerceapp.entity.Order;
import com.example.ecommerceapp.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

	private Integer orderId;

	private Integer customer;

	@NotNull(message="{order.products.null}")
	private List<ProductDTO> productDTOs;

	@NotNull(message="{order.totalCost.null}")
	private Float totalCost;

	private OrderStatus status;
	
	private LocalDateTime dateCreated;
	
	private LocalDateTime dateUpdated;
	
	private String paypalOrderId;
	
	public static OrderDTOBuilder entityToDto(Order order) {
		return OrderDTO.builder()
				.orderId(order.getOrderId())
				.customer(order.getCustomer().getCustomerId())
				.status(order.getStatus());
			
	}
}
