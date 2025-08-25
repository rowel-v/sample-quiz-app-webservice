package com.example.quizApp.service.teacher.sectionHandle.subjectHandle;

import java.util.function.Supplier;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.subject.AddSubjectRequest;
import com.example.quizApp.exception.SectionNotFoundException;
import com.example.quizApp.exception.TeacherNotFoundException;
import com.example.quizApp.exception.UnauthorizedException;
import com.example.quizApp.model.teacher.Teacher;
import com.example.quizApp.model.teacher.sectionHandle.Section;
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.Subject;
import com.example.quizApp.repo.teacher.account.TeacherAccountRepo;
import com.example.quizApp.repo.teacher.sectionHandle.SectionRepo;
import com.example.quizApp.result.teacher.subject.SubjectResult;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class SubjectService {

	private final TeacherAccountRepo teacherAccountRepo;
	private final SectionRepo sectionRepo;
	//private final SubjectRepo subjectRepo;

	private final Supplier<String> accountUsername = () -> {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return auth.getName();
		}
		throw new UnauthorizedException();
	};

	public SubjectResult.Add addSubject(String sectionName, AddSubjectRequest req) {
		return teacherAccountRepo.findByUsername(accountUsername.get())
				.map(acc -> {
					Teacher owner = acc.getTeacher();
					if (owner != null) {

						Section sectionOwner = owner.getSections().stream()
								.filter(section -> section.getName().equals(sectionName))
								.findFirst()
								.orElseThrow(() -> new SectionNotFoundException());

						Subject subjectReq = Subject.builder().name(req.getSubjectName()).build();
						
						boolean subjectAlreadyExists = sectionOwner.getSubjects().contains(subjectReq);

						if (subjectAlreadyExists) {
							return SubjectResult.Add.SUBJECT_ALREADY_EXISTS;
						}
						
						subjectReq.setSectionOwner(sectionOwner);
						sectionOwner.getSubjects().add(subjectReq);
						sectionRepo.save(sectionOwner);
						
						return SubjectResult.Add.SUBJECT_ADDED_SUCCESS;
					}
					throw new TeacherNotFoundException();
				})
				.orElseThrow(() -> new UnauthorizedException());
	}

}
