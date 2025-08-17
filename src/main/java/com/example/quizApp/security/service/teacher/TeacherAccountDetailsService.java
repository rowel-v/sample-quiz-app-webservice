package com.example.quizApp.security.service.teacher;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.quizApp.repo.teacher.account.TeacherAccountRepo;

import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class TeacherAccountDetailsService implements UserDetailsService {
	
	private final TeacherAccountRepo teacherAccountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return teacherAccountRepo.findByUsername(username).map(acc -> {
			return TeacherDetails.builder()
					.username(acc.getUsername())
					.password(acc.getPassword())
					.build();
		}).orElseThrow(() -> new UsernameNotFoundException("Account Not Found"));
	}

}
