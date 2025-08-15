package com.example.quizApp.exception;

public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8165218153996657468L;
	
	public UnauthorizedException() {
		super("No Authentication Found");
	}

}
