package com.example.quizApp.dto.student.account;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StudentAccountDto {
	
	@NotBlank 
	@Length(min = 5, max = 40)
	private String username;
	
	@NotBlank
	@Length(min = 10, max = 100)
	private String password;
}
