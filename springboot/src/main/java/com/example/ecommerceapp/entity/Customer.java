package com.example.ecommerceapp.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//this class implements user details to for authentication and to add additional fields 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	@Column(unique = true)
	private String username;
	private String email;
	private String password;
	
	@OneToMany(mappedBy="customer", cascade = CascadeType.ALL)
	private List<Order> orders;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<GrantedAuthority> authorities;
	
	public Customer(String username, String password, List<GrantedAuthority> authorities) {
		this.username= username;
		this.password= password;
		this.authorities = authorities;
	}
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
