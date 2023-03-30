package com.example.ecommerceapp.security;


import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.ecommerceapp.entity.Customer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtTokenService {

	// The secret key used to sign JWTs
	private final SecretKey jwtSecret;

	public JwtTokenService() {
	    this.jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}
    // Generate a JWT for the given authentication object
    public String generateToken(Authentication authentication) {
        // Get the customer object from the authentication
        Customer customer = (Customer) authentication.getPrincipal();
        
        // Get the current time
        Date now = new Date();
        
        // Set the token expiration time to one hour from now
        Date validity = new Date(now.getTime() + 3600000);

      
       
        
        // Build the JWT with the customer's username as the subject, the issue time as the current time,
        // the expiration time as one hour from now, and sign it with the secret key
        return Jwts.builder()
                .setSubject(customer.getUsername())
                .setIssuedAt(now)
                .setExpiration(validity)
                // Create a secret key from the JWT secret string
                .signWith(jwtSecret)
                .compact();
    }

    // Validate the given JWT
    public boolean validateToken(String token) {
        try {
            // Parse the JWT and verify its signature using the secret key
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            // If the JWT is valid, return true
            return true;
        } catch (JwtException e) {
            // If the JWT is invalid, return false
            return false;
        }
    }

    // Get the username from the given JWT
    public String getUsernameFromToken(String token) {
        // Parse the JWT and verify its signature using the secret key, then extract the claims
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        // Return the username from the claims
        return claims.getSubject();
    }

}
