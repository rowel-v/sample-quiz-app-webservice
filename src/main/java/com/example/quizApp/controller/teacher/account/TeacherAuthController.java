package com.example.quizApp.controller.teacher.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.shared.LoginRequestDTO;
import com.example.quizApp.service.teacher.account.TeacherAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
@RequestMapping("teacher")
public class TeacherAuthController {

	private final TeacherAccountService teacherAccountService;

	@PostMapping("login")
	ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
		String res = teacherAccountService.loginAccount(loginRequest);
		return ResponseEntity.ok(res);
	}
}
