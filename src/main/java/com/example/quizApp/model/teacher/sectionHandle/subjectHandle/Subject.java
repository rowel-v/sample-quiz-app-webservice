package com.example.quizApp.model.teacher.sectionHandle.subjectHandle;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.example.quizApp.model.teacher.sectionHandle.Section;
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle.Quiz;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "subject_handle", schema = "teacher_schema")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Getter @Setter
	private String name;

	@Setter @Getter
	@ManyToOne
	@JoinColumn(name = "section_owner", referencedColumnName = "name")
	private Section sectionOwner;
	
	@Getter
	@OneToMany(mappedBy = "subjectOwner", cascade = CascadeType.MERGE)
	private Set<Quiz> quizHandle = new HashSet<>();

	Subject() {}

	@Builder
	private Subject(String name) {
		super();
		this.name = name;
	}


	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		return Objects.equals(name, other.name);
	}

}
