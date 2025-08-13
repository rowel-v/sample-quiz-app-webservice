package com.example.quizApp.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.quizApp.exception.StudentIdentityNotFoundException;

@RestControllerAdvice
public class StudentExceptionHandler {
	
	@ExceptionHandler(value = StudentIdentityNotFoundException.class)
	ResponseEntity<?> studentIdentityNotFound(StudentIdentityNotFoundException e) {
		return ResponseEntity.notFound().build();
	}

}
