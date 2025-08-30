package com.example.quizApp.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.quizApp.exception.SubjectNotFoundException;

@RestControllerAdvice
public class SubjectExceptionHandler {
	
	@ExceptionHandler(value = SubjectNotFoundException.class)
	ResponseEntity<String> subjectNotFound(SubjectNotFoundException e) {
		return ResponseEntity.status(404).body("Subject not found");
	}

}
