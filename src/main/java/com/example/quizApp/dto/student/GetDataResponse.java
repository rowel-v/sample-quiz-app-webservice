package com.example.quizApp.dto.student;

import java.util.Map;

import lombok.Value;

@Value
public class GetDataResponse {
	
	private String firstname;
	private String lastname;
	private String fullname;
	private String section;
	
	private Map<String, String> account; // contains username and passsword
}