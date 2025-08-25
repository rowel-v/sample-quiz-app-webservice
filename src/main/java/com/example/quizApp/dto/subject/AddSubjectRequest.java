package com.example.quizApp.dto.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value @Builder
public class AddSubjectRequest {
	
	@NotBlank @Size(min = 1, max = 30)
	private String subjectName;
}
