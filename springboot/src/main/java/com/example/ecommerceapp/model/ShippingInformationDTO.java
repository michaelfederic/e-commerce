package com.example.ecommerceapp.model;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ecommerceapp.entity.ShippingInformation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingInformationDTO {
//	@NotNull(message="{shipping.shippingId.null}")
	private Integer shippingId;

	@NotNull(message="{shipping.recipientName.null}")
	private String recipientName;

	@NotNull(message="{shipping.addressLine1.null}")
	private String addressLine1;

	private String addressLine2;

	@NotNull(message="{shipping.city.null}")
	private String city;

	@NotNull(message="{shipping.state.null}")
	private String state;

	@NotNull(message="{shipping.postalCode.null}")
	private String postalCode;

	@NotNull(message="{shipping.country.null}")
	private String country;

	private String email;
	
	private OrderDetailsDTO order;
	//convert from entity to dto
	public static ShippingInformationDTOBuilder entityToDto(ShippingInformation shippingInformation) {
        return ShippingInformationDTO.builder()
                .shippingId(shippingInformation.getShippingId())
                .recipientName(shippingInformation.getRecipientName())
                .addressLine1(shippingInformation.getAddressLine1())
                .addressLine2(shippingInformation.getAddressLine2())
                .city(shippingInformation.getCity())
                .state(shippingInformation.getState())
                .postalCode(shippingInformation.getPostalCode())
                .country(shippingInformation.getCountry())
                .email(shippingInformation.getEmail())
                .order(OrderDetailsDTO.entityToDto(shippingInformation.getOrder()).build());
    }
}
