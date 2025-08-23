package com.example.quizApp.dto.teacher;

import java.util.Map;
import java.util.Set;

import com.example.quizApp.dto.section.SectionDataResponse;

import lombok.Value;

@Value
public class TeacherIdentityResponse {
	
	private String firstname;
	private String lastname;
	private String fullname;
	
	private Set<SectionDataResponse> handledSection;
	
	private Map<String, String> account; // contains username & password
}