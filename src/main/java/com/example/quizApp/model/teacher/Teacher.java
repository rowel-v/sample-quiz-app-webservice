package com.example.quizApp.model.teacher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Formula;

import com.example.quizApp.model.teacher.account.TeacherAccount;
import com.example.quizApp.model.teacher.sectionHandle.Section;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "teacher_identity", schema = "teacher_schema")
public class Teacher {
	
	Teacher() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter
	private String firstname;
	@Getter
	private String lastname;
	@Getter @Formula("concat(firstname, ' ', lastname)")
	private String fullname;
	
	@Setter @Getter
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "account_id")
	private TeacherAccount account;
	
	@Getter @Setter
	@OneToMany(mappedBy = "teacher", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	private Set<Section> sections = new HashSet<>();

	@Builder
	private Teacher(String firstname, String lastname, TeacherAccount account) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.account = account;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(firstname, lastname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		return Objects.equals(firstname, other.firstname) && Objects.equals(lastname, other.lastname);
	}
	
	
	
}
