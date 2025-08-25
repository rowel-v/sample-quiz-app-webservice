package com.example.quizApp.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.quizApp.exception.SectionNotFoundException;

@RestControllerAdvice
public class SectionExceptionHandler {
	
	@ExceptionHandler(value = SectionNotFoundException.class)
	ResponseEntity<String> sectionNotFound(SectionNotFoundException ex) {
		return ResponseEntity.status(404).body(ex.getMessage());
	}

}
