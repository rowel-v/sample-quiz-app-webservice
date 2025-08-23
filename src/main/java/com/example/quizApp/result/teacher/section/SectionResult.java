package com.example.quizApp.result.teacher.section;

public class SectionResult {
	
	public enum Add {
		SUCCESS,
		SECTION_ALREADY_ADDED
	}
	
	public enum Update {
		SUCCESS,
		SECTION_NOT_FOUND,
		SECTION_STILL_SAME,
		SECTION_ALREADY_EXISTS
	}
	
	public enum Delete {
		SUCCESS,
		SECTION_NOT_FOUND
	}

}
