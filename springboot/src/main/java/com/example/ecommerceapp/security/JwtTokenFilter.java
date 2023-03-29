package com.example.ecommerceapp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ecommerceapp.entity.Customer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private CustomerUserDetails customerUserDetails;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			// Get the JWT token from the request header
			String token = getToken(request);
			
			// If a valid token is present, authenticate the user and set the security context
			if(token != null && jwtTokenService.validateToken(token)) {
				String username = jwtTokenService.getUsernameFromToken(token);
				Customer customer = (Customer) customerUserDetails.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(customer, "", customer.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		} catch(Exception e) {
			// Throw a ServletException if authentication fails
			throw new ServletException("Cannot set authentication", e);
		}
		
		// Continue with the filter chain
		filterChain.doFilter(request, response);
		
	}
	
	private String getToken(HttpServletRequest request) {
		// Get the Authorization header from the request
		String authHeader = request.getHeader("Authorization");
		
		// If the header is present and starts with "Bearer ", return the token value
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		
		// Otherwise, return null
		return null;
	}
}
