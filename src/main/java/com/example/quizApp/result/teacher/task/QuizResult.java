package com.example.quizApp.result.teacher.task;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public class QuizResult {

	@Accessors(chain = true)
	@RequiredArgsConstructor
	public enum Add {
		
		QUZ_ANSWER_NOT_ON_CHOICES("Quiz answer letter [%s] not found on choices selection in subject %s"),

		QUIZ_NUMBER_ALREADY_EXISTS_AND_QUIZ_QUESTION_ALREADY_EXISTS("Quiz number %d and question \"%s\" already exists in subject %s."),

		QUIZ_NUMBER_ALREADY_EXISTS("Quiz number %d already exists in subject %s."),

		QUIZ_QUESTION_ALREADY_EXISTS("Quiz question \"%s\" already exists in subject %s."),

		QUIZ_ADDED_SUCCESS(null);

		private final String info;

		@Setter private char answer;
		@Setter private int quizNumber;
		@Setter private String quizQuestion;
		@Setter private String quizSubject;

		public String getInfo() {
			return switch (this) {
			case QUZ_ANSWER_NOT_ON_CHOICES -> String.format(info, answer, quizSubject);
			case QUIZ_NUMBER_ALREADY_EXISTS -> String.format(info, quizNumber, quizSubject);
			case QUIZ_QUESTION_ALREADY_EXISTS -> String.format(info, quizQuestion, quizSubject);
			case QUIZ_NUMBER_ALREADY_EXISTS_AND_QUIZ_QUESTION_ALREADY_EXISTS -> {
				yield String.format(info, quizNumber, quizQuestion, quizSubject);
			}
			case QUIZ_ADDED_SUCCESS -> null;
			};
		}
	}

	@RequiredArgsConstructor
	@Accessors(chain = true)
	public enum Delete {
		QUIZ_DELETE_SUCCESS(null),
		QUIZ_NUMBER_NOT_FOUND("Quiz number %d not found on subject %s.");
		
		private final String info;
		
		@Setter private int quizNumber;
		@Setter private String quizSubject;
		
		public String getInfo() {
			return switch (this) {
			case QUIZ_NUMBER_NOT_FOUND -> String.format(info, quizNumber, quizSubject);
			case QUIZ_DELETE_SUCCESS -> null;
			};
		}
	}
	
}
