package com.example.quizApp.service.student.account;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.student.account.StudentAccountDto;
import com.example.quizApp.mapper.student.account.StudentAccountMapper;
import com.example.quizApp.model.student.account.StudentAccount;
import com.example.quizApp.repo.student.account.StudentAccountRepo;
import com.example.quizApp.security.JwtUtil;
import com.example.quizApp.service.result.Result;
import com.example.quizApp.service.result.Result.Login;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentAccountService {
	
	private final StudentAccountRepo studentAccountRepo;
	private final PasswordEncoder pEncoder;
	private final JwtUtil jwtUtil;
	
	private final AuthenticationManager authManager;
	
	public Result.Signup createAccount(StudentAccountDto accountDto) {
		
		StudentAccount account = StudentAccountMapper.INSTANCE.toEntity(accountDto);
		
		return studentAccountRepo.findByUsername(account.getUsername())
				.map(a -> Result.Signup.USERNAME_ALREADY_TAKEN)
				.orElseGet(() -> {
					account.setPassword(pEncoder.encode(account.getPassword()));
					studentAccountRepo.save(account);
					return Result.Signup.SIGNUP_SUCCESS;
				});
	}
	
	public Result.Login loginAccount(StudentAccountDto accountDto) {
		
		StudentAccount account = StudentAccountMapper.INSTANCE.toEntity(accountDto);
		
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));
		
		if (!auth.isAuthenticated()) return Login.ACCOUNT_NOT_MATCH;
		
		Result.Login r = Login.LOGIN_SUCCESS;
		r.setData(jwtUtil.generateToken(auth.getName()));
		return r;
	}
}
