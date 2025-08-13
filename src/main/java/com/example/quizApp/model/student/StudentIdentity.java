package com.example.quizApp.model.student;

import com.example.quizApp.model.student.account.StudentAccount;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @Entity @NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "student_identity", schema = "student_schema")
public class StudentIdentity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY) @Setter(AccessLevel.NONE) 
	@Id private Long id;
	
	private String firstname;
	private String lastname;
	private String fullname;
	
	@OneToOne
	@JoinColumn(name = "account_id")
	private StudentAccount account;

	@Builder
	private StudentIdentity(String firstname, String lastname, StudentAccount account) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.account = account;
		this.fullname = firstname + " " + lastname;
	}
}
