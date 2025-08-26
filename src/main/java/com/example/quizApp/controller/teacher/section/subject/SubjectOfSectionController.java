package com.example.quizApp.controller.teacher.section.subject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.subject.AddSubjectRequest;
import com.example.quizApp.service.teacher.sectionHandle.subjectHandle.SubjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("teacher")
public class SubjectOfSectionController {
	
	private final SubjectService subjectService;
	
	@PostMapping("section/{sectionName}/addsubject")
	ResponseEntity<String> addSubject(@PathVariable String sectionName, @RequestBody @Valid AddSubjectRequest req) {
		
		var result = subjectService.addSubject(sectionName, req);
		var subjectName = req.getSubjectName();
		
		return switch (result) {
		case SUBJECT_ALREADY_EXISTS -> ResponseEntity.status(409).body("subject: " + subjectName + " already exists");
		case SUBJECT_ADDED_SUCCESS -> ResponseEntity.status(204).build();
		};
	}
}
