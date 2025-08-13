package com.example.quizApp.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<Map<String, String>> methodArgNotValid(MethodArgumentNotValidException e) {
		final Map<String, String> errors = new HashMap<>(e.getErrorCount());
		
		e.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}

}
