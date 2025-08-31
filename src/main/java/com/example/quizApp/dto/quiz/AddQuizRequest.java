package com.example.quizApp.dto.quiz;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Value;

@Value
public class AddQuizRequest {
	
	@NotNull @Min(value = 1)
	private Integer number;
	
	@NotBlank
	private String question;
	
	@NotNull @Size(min = 1, message = "must not be empty")
	private Set<@Valid AddQuizChoicesRequest> choices;
	
}
