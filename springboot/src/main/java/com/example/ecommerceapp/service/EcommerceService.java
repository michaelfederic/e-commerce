package com.example.ecommerceapp.service;

import java.util.List;

import com.example.ecommerceapp.model.CustomerDTO;
import com.example.ecommerceapp.model.OrderDTO;
import com.example.ecommerceapp.model.RegisterCustomerDTO;
import com.example.ecommerceapp.model.ResponseMessage;


public interface EcommerceService {
	
	public ResponseMessage authenticate(CustomerDTO customerDTO);
	public ResponseMessage register(RegisterCustomerDTO customerDTO);
	public CustomerDTO getDetails(String username);
	public List<OrderDTO> getOrders();
}
