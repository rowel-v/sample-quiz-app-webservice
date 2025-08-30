package com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Table(name = "quiz", schema = "teacher_schema")
@Entity @Getter @Setter
public class Quiz {
	
	@Id @Setter(value = AccessLevel.NONE)
	private Long id;
	
	@Column(name = "quiz_number")
	private int number;
	
	@Column(name = "quiz_question")
	private String question;
	
	@OneToMany(mappedBy = "quizOwner")
	private List<QuizChoices> choices = new ArrayList<>();
	
	@Builder
	private Quiz(int number, String question) {
		super();
		this.number = number;
		this.question = question;
	}

}
