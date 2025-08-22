package com.example.quizApp.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SaveIdentityRequest {
	
	@NotBlank @Size(min = 1, max = 20)
	private String firstname;
	@NotBlank @Size(min = 1, max = 30)
	private String lastname;

}
