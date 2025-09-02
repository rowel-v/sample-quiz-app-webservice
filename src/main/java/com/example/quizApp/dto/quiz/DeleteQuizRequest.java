package com.example.quizApp.dto.quiz;

import lombok.Builder;
import lombok.Value;

@Value @Builder
public class DeleteQuizRequest {
	
	private Integer quizNumber;
	private String sectionName;
	private String subjectName;
}
