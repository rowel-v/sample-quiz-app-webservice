package com.example.quizApp.controller.student.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.student.account.StudentAccountDto;
import com.example.quizApp.service.student.account.StudentAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
@RequestMapping("student")
public class StudentAuthController {
	
	private final StudentAccountService sAccountService;
	
	@PostMapping("login")
	public ResponseEntity<?> LoginReq(@RequestBody @Valid StudentAccountDto sAccountDto) {
		String result = sAccountService.loginAccount(sAccountDto);
		return ResponseEntity.ok(result);		
	}

}
