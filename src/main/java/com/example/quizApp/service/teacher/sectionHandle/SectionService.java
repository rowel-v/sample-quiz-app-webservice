package com.example.quizApp.service.teacher.sectionHandle;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.section.SectionDTO;
import com.example.quizApp.exception.TeacherNotFoundException;
import com.example.quizApp.exception.UnauthorizedException;
import com.example.quizApp.mapper.section.SectionMapper;
import com.example.quizApp.model.teacher.Teacher;
import com.example.quizApp.model.teacher.sectionHandle.Section;
import com.example.quizApp.repo.teacher.TeacherRepo;
import com.example.quizApp.repo.teacher.account.TeacherAccountRepo;
import com.example.quizApp.repo.teacher.sectionHandle.SectionRepo;
import com.example.quizApp.result.teacher.section.SectionResult;
import com.example.quizApp.result.teacher.section.SectionResult.Add;
import com.example.quizApp.result.teacher.section.SectionResult.Delete;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class SectionService {

	private final SectionRepo sectionRepo;
	private final TeacherAccountRepo teacherAccountRepo;
	private final TeacherRepo teacherRepo;

	private Supplier<String> accountUsername = () -> {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) return auth.getName();

		throw new UnauthorizedException();
	};

	public SectionResult.Add addSection(SectionDTO sectionDTO) {
		return teacherAccountRepo.findByUsername(accountUsername.get())
				.map(acc -> {
					Teacher teacher = acc.getTeacher();
					if (teacher != null) {
						Section section = SectionMapper.INSTANCE.toEntity(sectionDTO);
						var sectionAlreadyExists = sectionRepo.findAll().contains(section);
						if (sectionAlreadyExists) return Add.SECTION_ALREADY_ADDED;

						section.setTeacher(teacher);
						section.generateSectionCode();
						
						var sectionCodeAlreadyExists = sectionRepo.existsBySectionCode(section.getSectionCode());
						while (sectionCodeAlreadyExists) {
							section.generateSectionCode();
						}
						
						teacher.getSections().add(section);
						teacherRepo.save(teacher);
						return SectionResult.Add.SUCCESS;
					}
					throw new TeacherNotFoundException();
				}).orElseThrow(() -> new UnauthorizedException());
	}


	public Set<SectionDTO> getSectionsHandled() {
		return teacherAccountRepo.findByUsername(accountUsername.get())
				.map(acc -> {
					if (acc.getTeacher() != null) {
						return acc.getTeacher().getSections().stream()
								.map(SectionMapper.INSTANCE::toDTO)
								.collect(Collectors.toSet());
					}
					throw new TeacherNotFoundException();
				})
				.orElseThrow(() -> new UnauthorizedException());
	}

	public SectionResult.Delete deleteSection(String sectionName) {
		return sectionRepo.findByName(sectionName)
				.map(section -> {
					sectionRepo.delete(section);
					return Delete.SUCCESS;
				})
				.orElse(Delete.SECTION_NOT_FOUND);
	}

	public SectionResult.Update updateSection(String sectionNameToUpdate, SectionDTO sectionDTO) {
		return teacherAccountRepo.findByUsername(accountUsername.get())
				.map(acc -> {
					Teacher teacher = acc.getTeacher();
					if (teacher != null) {
						return sectionRepo.findByName(sectionNameToUpdate)
								.map(sectionToUpdate -> {
									sectionRepo.delete(sectionToUpdate);
									Section sectionToReplace = SectionMapper.INSTANCE.toEntity(sectionDTO);
									sectionToReplace.setTeacher(teacher);
									
									sectionToReplace.generateSectionCode();
									
									var sectionCodeAlreadyExists = sectionRepo.existsBySectionCode(sectionToReplace.getSectionCode());
									while (sectionCodeAlreadyExists) {
										sectionToReplace.generateSectionCode();
									}
									
									sectionRepo.save(sectionToReplace);
									return SectionResult.Update.SUCCESS;
								})
								.orElse(SectionResult.Update.SECTION_NOT_FOUND);
					}
					throw new TeacherNotFoundException();
				})
				.orElseThrow(() -> new UnauthorizedException());
	}


}
