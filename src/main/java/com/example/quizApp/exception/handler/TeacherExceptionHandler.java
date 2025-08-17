package com.example.quizApp.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.quizApp.exception.TeacherNotFoundException;

@RestControllerAdvice
public class TeacherExceptionHandler {
	
	@ExceptionHandler(value = TeacherNotFoundException.class)
	ResponseEntity<Void> teacherNotFound(TeacherNotFoundException e) {
		return ResponseEntity.status(404).build();
	}

}
