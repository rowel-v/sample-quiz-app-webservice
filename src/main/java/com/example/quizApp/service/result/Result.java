package com.example.quizApp.service.result;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {

	public enum Login {
		LOGIN_SUCCESS(null),
		ACCOUNT_NOT_MATCH(null);
		
		@Getter @Setter private String data;
		
		private Login(String data) {
			this.data = data;
		}
	}
	
	public enum Signup {
		SIGNUP_SUCCESS, 
		USERNAME_ALREADY_TAKEN
	}
	
	
	public enum Save {
		SAVE_SUCCESS	
	}
	

}
