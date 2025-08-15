package com.example.quizApp.model.teacher.sectionHandled;

import com.example.quizApp.model.teacher.Teacher;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "section_handle", schema = "teacher_schema")
public class Section {
	
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
	private Section(String name, String campus, int year, Teacher teacher) {
		super();
		this.name = name;
		this.campus = campus;
		this.year = year;
		this.teacher = teacher;
	}
}
