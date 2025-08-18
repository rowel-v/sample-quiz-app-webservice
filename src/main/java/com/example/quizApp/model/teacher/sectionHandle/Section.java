package com.example.quizApp.model.teacher.sectionHandle;

import java.util.Objects;

import com.example.quizApp.model.teacher.Teacher;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "section_handle", schema = "teacher_schema")
public class Section {
	
	Section() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter @Setter
	private String name;
	@Getter @Setter
	private String campus;
	@Getter @Setter
	private int year;
	
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "teacher_owner")
	private Teacher teacher;

	@Builder
	private Section(String name, String campus, int year) {
		super();
		this.name = name;
		this.campus = campus;
		this.year = year;
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
		Section other = (Section) obj;
		return Objects.equals(name, other.name);
	}
}
