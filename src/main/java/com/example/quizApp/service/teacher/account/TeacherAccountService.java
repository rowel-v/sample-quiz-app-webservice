package com.example.quizApp.service.teacher.account;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.teacher.account.TeacherAccountDto;
import com.example.quizApp.mapper.teacher.account.TeacherAccountMapper;
import com.example.quizApp.model.teacher.account.TeacherAccount;
import com.example.quizApp.repo.teacher.account.TeacherAccountRepo;
import com.example.quizApp.result.Result;
import com.example.quizApp.result.Result.Signup;
import com.example.quizApp.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class TeacherAccountService {

	private final TeacherAccountRepo teacherAccountRepo;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	
	// exception handled if BadCredentials has been throw in my exception.handler.AuthExceptionHandler
	public String loginRequest(TeacherAccountDto accountDto) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				accountDto.getUsername(), accountDto.getPassword()));
		
		if (authentication.isAuthenticated()) return jwtUtil.generateToken(accountDto.getUsername());
		
		return null;
	}
	
	public Result.Signup signupRequest(TeacherAccountDto accountDto) {
		
		return teacherAccountRepo.findByUsername(accountDto.getUsername())
				.map(r -> Signup.USERNAME_ALREADY_TAKEN)
				.orElseGet(() -> {
					TeacherAccount teacherAccount = TeacherAccountMapper.INSTANCE.toEntity(accountDto);
					teacherAccount.setPassword(passwordEncoder.encode(teacherAccount.getPassword()));
				    teacherAccountRepo.save(teacherAccount);
					return Signup.SIGNUP_SUCCESS;
				});
	}
}
