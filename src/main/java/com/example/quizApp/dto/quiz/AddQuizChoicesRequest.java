package com.example.quizApp.dto.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value @EqualsAndHashCode
public class AddQuizChoicesRequest {
	
	@NotNull
	private Character letter;
	
	@NotBlank @EqualsAndHashCode.Exclude
	private String selection;
}
