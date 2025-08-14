package com.example.quizApp.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;

@Builder
public class StudentDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2616860787052633379L;

	private final String username;
	private final String password;
	private final String fullname;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public String getFullname() {
		return fullname;
	}

}
