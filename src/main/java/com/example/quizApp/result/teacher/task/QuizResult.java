package com.example.quizApp.result.teacher.task;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class QuizResult {

	@RequiredArgsConstructor
	public enum Add {

		QUIZ_NUMBER_ALREADY_EXISTS_AND_QUIZ_QUESTION_ALREADY_EXISTS("Quiz number %d and question \"%s\" already exists in subject %s."),

		QUIZ_NUMBER_ALREADY_EXISTS("Quiz number %d already exists in subject %s."),

		QUIZ_QUESTION_ALREADY_EXISTS("Quiz question \"%s\" already exists in subject %s."),

		QUIZ_ADDED_SUCCESS(null);

		private final String info;

		@Setter private int quizNumber;
		@Setter private String quizQuestion;
		@Setter private String quizSubject;

		public String getInfo() {
			return switch (this) {
			case QUIZ_NUMBER_ALREADY_EXISTS -> String.format(info, quizNumber, quizSubject);
			case QUIZ_QUESTION_ALREADY_EXISTS -> String.format(info, quizQuestion, quizSubject);
			case QUIZ_NUMBER_ALREADY_EXISTS_AND_QUIZ_QUESTION_ALREADY_EXISTS -> {
				yield String.format(info, quizNumber, quizQuestion, quizSubject);
			}
			case QUIZ_ADDED_SUCCESS -> null;
			};
		}
	}

}
