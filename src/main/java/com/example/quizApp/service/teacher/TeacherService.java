package com.example.quizApp.service.teacher;

import java.util.function.Supplier;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.teacher.TeacherDto;
import com.example.quizApp.exception.UnauthorizedException;
import com.example.quizApp.mapper.teacher.TeacherMapper;
import com.example.quizApp.model.teacher.Teacher;
import com.example.quizApp.repo.teacher.TeacherRepo;
import com.example.quizApp.repo.teacher.account.TeacherAccountRepo;
import com.example.quizApp.result.Result;
import com.example.quizApp.result.Result.Save;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class TeacherService {
	
	private final TeacherRepo teacherRepo;
	private final TeacherAccountRepo teacherAccountRepo;
	
	private Supplier<String> accountUsername = () -> {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) return auth.getName();
		throw new UnauthorizedException();
	};
	
	public Result.Save saveIdentity(TeacherDto teacherDto) {
		return teacherAccountRepo.findByUsername(accountUsername.get()).map(acc -> {
			if (acc.getTeacher() != null) return Save.ALREADY_SAVE;
			Teacher teacher = Teacher.builder()
					.firstname(teacherDto.getFirstname())
					.lastname(teacherDto.getLastname())
					.build();
			acc.setTeacher(teacher);
			teacherAccountRepo.save(acc);
			return Save.SAVE_SUCCESS;
		}).orElseThrow(() -> new UsernameNotFoundException("Account not found"));	
	}
	
	public void updateIdentity(TeacherDto teacherDto) {
		teacherAccountRepo.findByUsername(accountUsername.get()).ifPresent(acc -> {
			Teacher identityReq = TeacherMapper.INSTANCE.toEntity(teacherDto);
			Teacher identity = acc.getTeacher();
			if (!identity.equals(identityReq)) {
				identity.setFirstname(identityReq.getFirstname());
				identity.setLastname(identityReq.getLastname());
				teacherRepo.save(identity);
			}
		});
	}
	

}
