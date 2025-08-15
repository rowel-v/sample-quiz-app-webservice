package com.example.quizApp.model.teacher;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class TeacherIdentity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final Long id = null;
	
	@NotNull
	@Length(min = 2)
	private String firstname;
	
	@NotNull
	@Length(min = 2)
	private String lastname;
}
