package com.example.quizApp.service.teacher.account;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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
	private final AuthenticationProvider teacherAuthProvider;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder teacherPasswordEncoder;

	// exception handled if BadCredentials has been throw in my exception.handler.AuthExceptionHandler
	public String loginAccount(TeacherAccountDto accountDto) {

		Authentication auth = teacherAuthProvider.authenticate(new UsernamePasswordAuthenticationToken(
				accountDto.getUsername(), accountDto.getPassword()));

		if (auth.isAuthenticated()) return jwtUtil.generateToken(auth.getName());

		throw new BadCredentialsException("Invalid Credentials");
	}

	public Result.Signup signupRequest(TeacherAccountDto accountDto) {

		return teacherAccountRepo.findByUsername(accountDto.getUsername())
				.map(r -> Signup.USERNAME_ALREADY_TAKEN)
				.orElseGet(() -> {
					TeacherAccount teacherAccount = TeacherAccountMapper.INSTANCE.toEntity(accountDto);
					teacherAccount.setPassword(teacherPasswordEncoder.encode(teacherAccount.getPassword()));
					teacherAccountRepo.save(teacherAccount);
					return Signup.SIGNUP_SUCCESS;
				});
	}
}
