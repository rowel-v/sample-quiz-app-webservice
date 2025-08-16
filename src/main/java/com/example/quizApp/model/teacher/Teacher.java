package com.example.quizApp.model.teacher;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.annotations.Formula;

import com.example.quizApp.model.teacher.account.TeacherAccount;
import com.example.quizApp.model.teacher.sectionHandled.Section;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "teacher_identity", schema = "teacher_schema")
public class Teacher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter
	private String firstname;
	@Getter
	private String lastname;
	@Getter @Formula("concat(firstname, ' ', fulname)")
	private String fullname;
	
	@OneToOne
	@JoinColumn(name = "account_id")
	private TeacherAccount account;
	
	@Getter // TODO setter vague
	@OneToMany(mappedBy = "teacher")
	private final List<Section> sections = new LinkedList<>();

	@Builder
	private Teacher(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		updateFullname();
	}
	
	private void updateFullname() {
		this.fullname = firstname + " " + lastname; 
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
		updateFullname();
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
		updateFullname();
	}
}
