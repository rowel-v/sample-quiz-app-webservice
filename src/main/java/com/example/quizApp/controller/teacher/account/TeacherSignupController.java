package com.example.quizApp.controller.teacher.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.teacher.account.TeacherAccountDto;
import com.example.quizApp.result.Result.Signup;
import com.example.quizApp.service.teacher.account.TeacherAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
@RequestMapping("teacher")
public class TeacherSignupController {
	
	private final TeacherAccountService teacherAccountService;
	
	@PostMapping("signup")
	ResponseEntity<Void> signup(@RequestBody @Valid TeacherAccountDto teacherAccountDto) {
		Signup res = teacherAccountService.signupRequest(teacherAccountDto);
		return switch (res) {
		case SIGNUP_SUCCESS -> ResponseEntity.status(204).build();
		case USERNAME_ALREADY_TAKEN -> ResponseEntity.status(409).build();
		};
	}
}
