package com.example.quizApp.dto.teacher.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class TeacherAccountDto {
	
	@NotBlank @Size(min = 10, max = 15)
	@Getter @Setter
	private String username;
	
	@NotBlank @Size(min = 15, max = 30)
	@Getter @Setter
	private String password;
}
