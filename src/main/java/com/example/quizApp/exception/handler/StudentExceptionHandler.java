package com.example.quizApp.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.quizApp.exception.StudentAlreadySaveException;

@RestControllerAdvice
public class StudentExceptionHandler {
	
	@ExceptionHandler(value = StudentAlreadySaveException.class)
	ResponseEntity<String> alreadySave(StudentAlreadySaveException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

}
