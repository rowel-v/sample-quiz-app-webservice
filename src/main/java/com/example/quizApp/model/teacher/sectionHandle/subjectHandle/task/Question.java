package com.example.quizApp.model.teacher.sectionHandle.subjectHandle.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "quiz_question")
public class Question {
	
	@Id @Setter(value = AccessLevel.NONE)
	private Long id;
	
	@Column(name = "question_number")
	private int number;
	private String question;
	private boolean answer;
	
	@ManyToOne
	@JoinColumn(name = "quiz_number")
	private Quiz quizOwner;
	
	@Builder
	private Question(String question, boolean answer) {
		super();
		this.question = question;
		this.answer = answer;
	}
}
