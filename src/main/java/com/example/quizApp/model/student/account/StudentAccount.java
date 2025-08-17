package com.example.quizApp.model.student.account;

import java.time.LocalDateTime;

import com.example.quizApp.model.student.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "student_account", schema = "student_schema")
@Data @Entity
public class StudentAccount {
	
	StudentAccount() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Setter(value = AccessLevel.NONE)
	private Long id;
	
	private String username;
	private String password;
	
	private LocalDateTime createdAt;
	
	@OneToOne(mappedBy = "account", fetch = FetchType.LAZY, orphanRemoval = true)
	@Getter
	@Setter(value = AccessLevel.NONE)
	private Student student;

	@Builder
	private StudentAccount(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		createdAt = LocalDateTime.now();
	}
}
