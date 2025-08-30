package com.example.quizApp.result.teacher.task;

import lombok.Getter;
import lombok.Setter;

public class QuizResult {
	
	public enum Add {
		
		QUIZ_NUMBER_ALREADY_EXISTS_AND_QUIZ_QUESTION_ALREADY_EXISTS,
		
		QUIZ_NUMBER_ALREADY_EXISTS,
		
		QUIZ_QUESTION_ALREADY_EXISTS,
		
		QUIZ_ADDED_SUCCESS;
		
		@Getter @Setter
		private String info;
	}

}
