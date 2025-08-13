package com.example.quizApp.controller.student.entry;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.student.account.StudentAccountDto;
import com.example.quizApp.service.result.Result.Signup;
import com.example.quizApp.service.student.account.StudentAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
public class SignupController {
	
	private final StudentAccountService service;
	
	@PostMapping("signup")
	ResponseEntity<?> signupReq(@RequestBody @Valid StudentAccountDto sDto) {
		
		Signup result = service.createAccount(sDto); 
		return switch (result) {
		case SIGNUP_SUCCESS -> ResponseEntity.created(URI.create("login")).build();
		case USERNAME_ALREADY_TAKEN -> ResponseEntity.status(HttpStatus.CONFLICT).build();
		};
	}

}
