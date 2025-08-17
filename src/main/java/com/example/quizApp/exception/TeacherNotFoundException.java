package com.example.quizApp.exception;

public class TeacherNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2866657839803092016L;

	public TeacherNotFoundException() {
		super("Teacher Identity has not been set");
	}

}
