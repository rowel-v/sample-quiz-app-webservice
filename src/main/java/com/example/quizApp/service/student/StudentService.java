package com.example.quizApp.service.student;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.student.StudentDto;
import com.example.quizApp.exception.StudentIdentityNotFoundException;
import com.example.quizApp.mapper.student.StudentMapper;
import com.example.quizApp.model.student.StudentIdentity;
import com.example.quizApp.repo.student.StudentRepo;
import com.example.quizApp.repo.student.account.StudentAccountRepo;
import com.example.quizApp.security.service.StudentDetails;
import com.example.quizApp.service.result.Result;
import com.example.quizApp.service.result.Result.Save;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class StudentService {

	private final StudentAccountRepo studentAccountRepo;
	private final StudentRepo studentRepo;

	private Supplier<String> username = () -> SecurityContextHolder.getContext().getAuthentication().getName();
	private Supplier<StudentDetails> studentDetails = () -> (StudentDetails) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	// student can be save one time only, it can be update in Result.Update
	public Result.Save saveIdentity(StudentDto studentDto) {
		StudentIdentity student = StudentMapper.INSTANCE.toEntity(studentDto);

		var username = this.username.get();
		var account = studentAccountRepo.findByUsername(username).get();

		if (account.getStudent() != null) return Save.ALREADY_SAVE;

		student.setAccount(account);
		studentRepo.save(student);
		return Save.SAVE_SUCCESS;
	}

	public StudentDto getIdentity() {
		return studentAccountRepo.findByUsername(username.get())
				.map(acc -> StudentMapper.INSTANCE.tDto(acc.getStudent()))
				.orElseThrow(() -> new StudentIdentityNotFoundException());	
	}

	public void updateIdentity(StudentDto studentDto) {
		studentAccountRepo.findByUsername(username.get()).ifPresent(acc -> {
			StudentIdentity request = StudentMapper.INSTANCE.toEntity(studentDto);
			// check the request data if not equal in a persisted entity (i.e., firstname, lastname & fullname)
			if (!acc.getStudent().equals(request)) {
				acc.getStudent().setFirstname(studentDto.getFirstname());
				acc.getStudent().setLastname(studentDto.getLastname());
				studentAccountRepo.save(acc);
			}
		});
	}
	
	@Transactional
	public void deleteAccount() {		
		studentRepo.deleteByFullname(studentDetails.get().getFullname());
	}
	
	public List<StudentDto> getAllIdentity() {	
		return studentRepo.findAll().stream().map(StudentMapper.INSTANCE::tDto).collect(Collectors.toList());
	}



}
