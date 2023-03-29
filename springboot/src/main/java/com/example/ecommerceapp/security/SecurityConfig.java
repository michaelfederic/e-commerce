package com.example.ecommerceapp.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	CustomerUserDetails customerUserDetails;
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	
	
	/* AUTHENTICATION
	 * This method configures the DaoAuthenticationProvider to use CustomerUserDetails as the UserDetailsService and the 
	 * BCryptPasswordEncoder to encode passwords. This is used to authenticate users for the application.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customerUserDetails);
		authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		return authenticationProvider;
	}
	/* SECURITY FILTER CHAIN
	 * This method configures the access control rules for HTTP requests using HttpSecurity.
	 * Here, requests to /api/auth/** are permitted to all, requests to /api/user/** are only allowed for users with the "USER" role,
	 * and all other requests are denied.
	 * The SecurityFilterChain is returned to configure the security filter chain.
	 */
	@Bean
	SecurityFilterChain web(HttpSecurity http) throws Exception{
		http
			.cors().and().csrf().disable();
		
		//configuring authorization for Spring Security
		http
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/api/auth/**").permitAll()
					.requestMatchers("/api/user/**").hasRole("USER")
					.anyRequest().denyAll());
		
		//session management for Spring Security in a way that is appropriate for JWT (JSON Web Tokens) authentication.
		http
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		//configuring the authentication provider for Spring Security.
		http
			.authenticationProvider(authenticationProvider());
		
		//configure the jwt filter
		http
			.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	
}
