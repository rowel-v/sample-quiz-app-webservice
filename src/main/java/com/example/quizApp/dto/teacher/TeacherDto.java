package com.example.quizApp.dto.teacher;

import com.example.quizApp.dto.teacher.account.TeacherAccountDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeacherDto {
	
	@NotBlank @Size(max = 40)
	private String firstname;
	
	@NotBlank @Size(max = 40)
	private String lastname;
	
	private TeacherAccountDto account;

}
