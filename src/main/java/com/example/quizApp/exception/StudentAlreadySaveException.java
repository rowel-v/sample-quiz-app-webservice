package com.example.quizApp.exception;

public class StudentAlreadySaveException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StudentAlreadySaveException() {
		super("Student already Save");
	}

}
