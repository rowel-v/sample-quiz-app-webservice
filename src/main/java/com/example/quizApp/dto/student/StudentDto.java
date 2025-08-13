package com.example.quizApp.dto.student;

import com.example.quizApp.dto.student.account.StudentAccountDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentDto {
	
	@NotBlank
	private String firstname;
	@NotBlank
	private String lastname;
	
	@NotNull
	private StudentAccountDto account;
		
}
