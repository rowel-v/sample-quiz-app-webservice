package com.example.quizApp.dto.student;

import com.example.quizApp.dto.section.SectionDTO;
import com.example.quizApp.dto.shared.AccountDTO;

import lombok.Value;

@Value
public class DataResponse {
	
	private String firstname;
	private String lastname;
	private String fullname;
	
	private AccountDTO account;
	private SectionDTO section;
}