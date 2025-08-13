package com.example.quizApp.controller.student.entry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.student.account.StudentAccountDto;
import com.example.quizApp.service.result.Result;
import com.example.quizApp.service.student.account.StudentAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
public class AuthController {
	
	private final StudentAccountService sAccountService;
	
	@PostMapping("login")
	ResponseEntity<?> LoginReq(@RequestBody @Valid StudentAccountDto sAccountDto) {
		
		Result.Login res = sAccountService.loginAccount(sAccountDto);
		
		return switch (res) {
		case LOGIN_SUCCESS -> ResponseEntity.ok().body(res.getData());
		case ACCOUNT_NOT_MATCH -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		};
	}

}
