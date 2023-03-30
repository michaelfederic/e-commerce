package com.example.ecommerceapp.model;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ecommerceapp.entity.OrderDetails;
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
public class OrderDetailsDTO {
//	@NotNull(message="{order.orderId.null}")
	private Integer orderId;

	private Integer customer;

	@NotNull(message="{order.products.null}")
	private List<ProductDTO> productDTOs;

	@NotNull(message="{order.totalCost.null}")
	private Integer totalCost;

	private List<ShippingInformationDTO> shippingInformationDTOs;
	
	@NotNull(message= "{order.payment.null}")
	private PaymentInformationDTO paymentInformationDTO;

//	@NotNull(message="{order.status.null}")
	private OrderStatus status;
	
	private LocalDateTime dateCreated;
	
	private LocalDateTime dateUpdated;
	
	public static OrderDetailsDTOBuilder entityToDto(OrderDetails orderDetails) {
		List<ProductDTO> productDTOs = orderDetails
				.getProducts()
				.stream()
				.map(product -> ProductDTO.entityToDto(product).build())
				.toList();
		List<ShippingInformationDTO> shippingInformationDTOs = orderDetails
				.getShippingInformation()
				.stream()
				//using .build returns the instance of the this class i.e ShippingInformationDTO
				.map(s-> ShippingInformationDTO.entityToDto(s).build())
				.toList();
		PaymentInformationDTO paymentInformationDTO = PaymentInformationDTO.entityToDto(orderDetails.getPaymentInformation()).build();
		return OrderDetailsDTO.builder()
				.orderId(orderDetails.getOrderId())
				.customer(orderDetails.getCustomer().getCustomerId())
				.productDTOs(productDTOs)
				.totalCost(orderDetails.getTotalCost())
				.shippingInformationDTOs(shippingInformationDTOs)
				.paymentInformationDTO(paymentInformationDTO)
				.status(orderDetails.getStatus())
				.dateCreated(orderDetails.getDateCreated())
				.dateUpdated(orderDetails.getDateUpdated());
	}
}
