package com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle;

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
import lombok.Setter;

@Entity @Table(name = "quiz_choices", schema = "teacher_schema")
@Setter @Getter
public class QuizChoices {
	
	@Id @Setter(value = AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "question")
	private Quiz quizOwner;
	
	private char letter;
	private String selection;
	
	@Builder
	private QuizChoices(Quiz quizOwner, char letter, String selection) {
		super();
		this.quizOwner = quizOwner;
		this.letter = letter;
		this.selection = selection;
	}	
		
}
