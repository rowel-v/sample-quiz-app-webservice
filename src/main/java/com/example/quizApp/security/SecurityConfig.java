package com.example.quizApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.quizApp.security.filter.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration @RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthFilter jAuthFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
				.csrf(c -> c.disable())
				.authorizeHttpRequests(req -> req
						.requestMatchers(HttpMethod.GET, "/student")
						.permitAll()
						.requestMatchers(HttpMethod.POST, "/student/login", "/student/signup")
						.permitAll()
						.requestMatchers(HttpMethod.POST, "/teacher/login", "/teacher/signup")
						.permitAll()
						.anyRequest()
						.authenticated())
				.addFilterBefore(jAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		
	}
	
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration aConfiguration) throws Exception {
		return aConfiguration.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
