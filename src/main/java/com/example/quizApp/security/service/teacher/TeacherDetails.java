package com.example.quizApp.security.service.teacher;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;

@Builder
public class TeacherDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2944934916110434634L;
	
	private String username;
	private String password;
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

}
