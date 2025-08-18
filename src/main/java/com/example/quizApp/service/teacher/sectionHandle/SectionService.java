package com.example.quizApp.service.teacher.sectionHandle;

import java.util.function.Supplier;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.section.SectionDTO;
import com.example.quizApp.exception.TeacherNotFoundException;
import com.example.quizApp.exception.UnauthorizedException;
import com.example.quizApp.mapper.section.SectionMapper;
import com.example.quizApp.model.teacher.Teacher;
import com.example.quizApp.repo.teacher.TeacherRepo;
import com.example.quizApp.repo.teacher.account.TeacherAccountRepo;
import com.example.quizApp.result.section.SectionResult;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class SectionService {

	private final TeacherAccountRepo teacherAccountRepo;
	private final TeacherRepo teacherRepo;

	private Supplier<String> accountUsername = () -> {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) return auth.getName();

		throw new UnauthorizedException();
	};

	public SectionResult.Add addSection(SectionDTO sectionDTO) {
		return teacherAccountRepo.findByUsername(accountUsername.get()).map(acc -> {
			Teacher teacher = acc.getTeacher();

			if (teacher != null) {
				SectionResult.Add res = teacher.addSection(SectionMapper.INSTANCE.toEntity(sectionDTO));
				return switch (res) {
				case SECTION_ALREADY_ADDED -> res;
				case SECTION_ADDED -> {
					teacherRepo.save(teacher);
					yield res;
				}
				};
			}
			throw new TeacherNotFoundException();
		}).orElseThrow(() -> new UnauthorizedException());
	}




}
