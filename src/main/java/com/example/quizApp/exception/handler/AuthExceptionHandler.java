package com.example.quizApp.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

	@ExceptionHandler(exception = BadCredentialsException.class)
	ResponseEntity<?> invalidUsernameAndPassword(BadCredentialsException e) {
		return ResponseEntity.status(401).build();
	}
}
