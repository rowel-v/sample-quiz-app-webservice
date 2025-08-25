package com.example.quizApp.exception;

public class SectionNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1294434023884614368L;

	public SectionNotFoundException() {
		super("Section Not Found");
	}
}
