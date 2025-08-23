package com.example.quizApp.dto.shared;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class SignupRequest {
	
	@NotBlank 
	@Length(min = 5, max = 40)
	private String username;
	
	@NotBlank
	@Length(min = 10, max = 100)
	private String password;
}
