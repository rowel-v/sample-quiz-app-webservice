package com.example.quizApp.service.student.account;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.student.account.StudentAccountDto;
import com.example.quizApp.mapper.student.account.StudentAccountMapper;
import com.example.quizApp.model.student.account.StudentAccount;
import com.example.quizApp.repo.student.account.StudentAccountRepo;
import com.example.quizApp.result.Result;
import com.example.quizApp.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentAccountService {
	
	private final StudentAccountRepo studentAccountRepo;
	private final PasswordEncoder studentPasswordEncoder;
	private final JwtUtil jwtUtil;
	
	private final AuthenticationProvider studentAuthProvider;
	
	public Result.Signup createAccount(StudentAccountDto accountDto) {
		
		StudentAccount account = StudentAccountMapper.INSTANCE.toEntity(accountDto);
		
		return studentAccountRepo.findByUsername(account.getUsername())
				.map(a -> Result.Signup.USERNAME_ALREADY_TAKEN)
				.orElseGet(() -> {
					account.setPassword(studentPasswordEncoder.encode(account.getPassword()));
					studentAccountRepo.save(account);
					return Result.Signup.SIGNUP_SUCCESS;
				});
	}
	
	// exception handled if BadCredentials has been throw in my exception.handler.AuthExceptionHandler
	public String loginAccount(StudentAccountDto accountDto) {
		
		Authentication auth = studentAuthProvider.authenticate(new UsernamePasswordAuthenticationToken(
				accountDto.getUsername(), accountDto.getPassword()));
		
		if (auth.isAuthenticated()) return jwtUtil.generateToken(auth.getName());
		
		throw new BadCredentialsException("Invalid Credentials");
	}
}
