package com.example.quizApp.model.teacher.account;

import com.example.quizApp.model.teacher.Teacher;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "teacher_account", schema = "teacher_schema")
public class TeacherAccount {
	
	@Id @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter @Setter
	private String username;
	@Getter @Setter
	private String password;
	
	@Getter @Setter
	@OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
	private Teacher teacher;
	
	@Builder
	public TeacherAccount(String username, String password, Teacher teacher) {
		super();
		this.username = username;
		this.password = password;
		this.teacher = teacher;
	}
}
