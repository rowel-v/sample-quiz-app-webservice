package com.example.quizApp.dto.section;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UpdateSectionRequest {
	
	@NotBlank @Size(min = 1, max = 20)
	private String name;
	@NotBlank @Size(min = 1, max = 20)
	private String campus;
	@Min(value = 1) @Max(value = 5)
	private int year;
}
