package com.example.quizApp.dto.quiz;

import java.util.Set;

import lombok.Value;

@Value
public class AddQuizRequest {
	
	private int number;
	private String question;
	private Set<AddQuizChoicesRequest> choices;
}
