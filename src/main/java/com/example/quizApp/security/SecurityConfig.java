package com.example.quizApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.quizApp.security.filter.JwtAuthFilter;
import com.example.quizApp.security.service.student.StudentAccountDetailsService;
import com.example.quizApp.security.service.teacher.TeacherAccountDetailsService;

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
	
	private final StudentAccountDetailsService studentAccountDetailsService;
	private final TeacherAccountDetailsService teacherAccountDetailsService;
	
	@Bean
	AuthenticationProvider studentAuthProvider() {
		DaoAuthenticationProvider dProvider = new DaoAuthenticationProvider(studentAccountDetailsService);
		dProvider.setPasswordEncoder(passwordEncoder());
		return dProvider;
	}
	
	@Bean
	AuthenticationProvider teacherAuthProvider() {
		DaoAuthenticationProvider dProvider = new DaoAuthenticationProvider(teacherAccountDetailsService);
		dProvider.setPasswordEncoder(passwordEncoder());
		return dProvider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
