package com.example.quizApp.exception;

public class SubjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2256633989997924229L;

	public SubjectNotFoundException() {
		super("Subject Not Found");
	}

	
}
