package com.example.quizApp.dto.subject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value @Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectDTO {
	
	private String name;
	
}
