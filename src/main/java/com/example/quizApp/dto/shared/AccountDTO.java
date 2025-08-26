package com.example.quizApp.dto.shared;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value @Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDTO {
	
	private String username;
	private String password;

}
