package com.example.quizApp.model.student;

import java.util.Objects;

import org.hibernate.annotations.Formula;

import com.example.quizApp.model.student.account.StudentAccount;
import com.example.quizApp.model.teacher.sectionHandle.Section;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "student_identity", schema = "student_schema")
public class Student {
	
	Student() {}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY) @Setter(AccessLevel.NONE) 
	@Id private Integer id;
	
	@Getter
	private String firstname;
	@Getter
	private String lastname;
	
	@Getter @Formula("concat(firstname, ' ', lastname)")
	private String fullname;
	
	@Getter @Setter
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "section", referencedColumnName = "name")
	private Section section;
	
	@Getter @Setter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private StudentAccount account;

	@Builder
	private Student(String firstname, String lastname, StudentAccount account) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.account = account;
		updateFullname();
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
		updateFullname();
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
		updateFullname();
	}
	
	private void updateFullname() {
		this.fullname = firstname + " " + lastname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstname, fullname, lastname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(firstname, other.firstname) && Objects.equals(fullname, other.fullname)
				&& Objects.equals(lastname, other.lastname);
	}
	
	
}
