package com.example.quizApp.dto.section;

import java.util.Set;

import com.example.quizApp.dto.subject.SubjectDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value @Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SectionDTO {
	
	private String name;
	
	private Set<SubjectDTO> subject;
	
}
