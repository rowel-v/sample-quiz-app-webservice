package com.example.quizApp.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.quizApp.exception.StudentNotFoundException;

@RestControllerAdvice
public class StudentExceptionHandler {
	
	@ExceptionHandler(value = StudentNotFoundException.class)
	ResponseEntity<?> studentIdentityNotFound(StudentNotFoundException e) {
		return ResponseEntity.notFound().build();
	}

}
