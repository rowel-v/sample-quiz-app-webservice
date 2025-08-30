package com.example.quizApp.dto.quiz;

import lombok.Value;

@Value
public class AddQuizChoicesRequest {
	
	private char letter;
	private String selection;
}
