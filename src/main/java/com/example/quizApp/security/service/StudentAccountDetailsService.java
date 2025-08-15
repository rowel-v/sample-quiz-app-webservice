package com.example.quizApp.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.quizApp.model.student.account.StudentAccount;
import com.example.quizApp.repo.student.account.StudentAccountRepo;

import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class StudentAccountDetailsService implements UserDetailsService {

	private final StudentAccountRepo studentAccountRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		StudentAccount acc = studentAccountRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("not found username"));
		
		return StudentDetails.builder()
		.username(acc.getUsername())
		.password(acc.getPassword())
		.accountId(acc.getId())
		.build();
	}

}
