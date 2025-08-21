package com.example.quizApp.service.student;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.student.StudentDto;
import com.example.quizApp.exception.StudentNotFoundException;
import com.example.quizApp.exception.UnauthorizedException;
import com.example.quizApp.mapper.student.StudentMapper;
import com.example.quizApp.model.student.Student;
import com.example.quizApp.repo.student.StudentRepo;
import com.example.quizApp.repo.student.account.StudentAccountRepo;
import com.example.quizApp.result.Result;
import com.example.quizApp.result.Result.Save;
import com.example.quizApp.security.service.student.StudentDetails;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class StudentService {

	private final StudentAccountRepo studentAccountRepo;
	private final StudentRepo studentRepo;

	private Supplier<String> studentUsername = () -> SecurityContextHolder.getContext().getAuthentication().getName();
	private Supplier<StudentDetails> studentDetails = () -> {
		
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) throw new UnauthorizedException();
		
		return (StudentDetails) auth.getPrincipal();
	};

	// student can be save one time only, it can be update in Result.Update
	public Result.Save saveIdentity(StudentDto studentDto) {
		Student student = StudentMapper.INSTANCE.toEntity(studentDto);

		var username = this.studentUsername.get();
		var account = studentAccountRepo.findByUsername(username).get();

		if (account.getStudent() != null) return Save.ALREADY_SAVE;

		student.setAccount(account);
		studentRepo.save(student);
		return Save.SAVE_SUCCESS;
	}

	public StudentDto getIdentity() {
		return studentAccountRepo.findByUsername(studentUsername.get())
				.map(acc -> StudentMapper.INSTANCE.toDto(acc.getStudent()))
				.orElseThrow(() -> new StudentNotFoundException());	
	}

	public void updateIdentity(StudentDto studentDto) {
		studentAccountRepo.findByUsername(studentUsername.get()).ifPresent(acc -> {
			Student request = StudentMapper.INSTANCE.toEntity(studentDto);
			// check the request data if not equal in a persisted entity (i.e., firstname, lastname & fullname)
			if (acc.getStudent() == null) throw new StudentNotFoundException();
			if (!acc.getStudent().equals(request)) {
				acc.getStudent().setFirstname(studentDto.getFirstname());
				acc.getStudent().setLastname(studentDto.getLastname());
				studentAccountRepo.save(acc);
			}
		});
	}
	
	@Transactional
	public void deleteAccount() {	
		studentAccountRepo.deleteById(studentDetails.get().getAccountId());
	}
	
	public List<StudentDto> getAllIdentity() {	
		return studentRepo.findAll().stream().map(StudentMapper.INSTANCE::toDto).collect(Collectors.toList());
	}



}
