package com.example.quizApp.service.student;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.student.StudentDto;
import com.example.quizApp.exception.StudentAlreadySaveException;
import com.example.quizApp.exception.handler.StudentIdentityNotFoundException;
import com.example.quizApp.mapper.student.StudentMapper;
import com.example.quizApp.model.student.StudentIdentity;
import com.example.quizApp.repo.student.StudentRepo;
import com.example.quizApp.repo.student.account.StudentAccountRepo;
import com.example.quizApp.service.result.Result;
import com.example.quizApp.service.result.Result.Save;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class StudentService {
	
	private final StudentAccountRepo studentAccountRepo;
	private final StudentRepo studentRepo;
	
	// student can be save one time only, it can be update in Result.Update
	public Result.Save saveIdentity(StudentDto studentDto) {
		StudentIdentity student = StudentMapper.INSTANCE.toEntity(studentDto);
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		studentAccountRepo.findByUsername(username)
		.ifPresentOrElse(studentAccount -> {	
			if (studentAccount.getStudent() != null) throw new StudentAlreadySaveException();
			
			student.setAccount(studentAccount);
			studentRepo.save(student);
			
			System.out.println(student);
		}, () -> System.out.println("rowel"));
		
		return Save.SAVE_SUCCESS;
	}
	
	public StudentDto getIdentity() {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return studentAccountRepo.findByUsername(username)
				.map(acc -> StudentMapper.INSTANCE.tDto(acc.getStudent()))
				.orElseThrow(() -> new StudentIdentityNotFoundException());	
	}
	
}
