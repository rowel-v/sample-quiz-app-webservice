package com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle;

import java.util.HashSet;
import java.util.Set;

import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.Subject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "quiz_number")
	private int number;
	
	@Column(name = "quiz_question")
	private String question;
	
	@OneToMany(mappedBy = "quizOwner", cascade = CascadeType.PERSIST)
	private Set<QuizChoices> choices = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "subject", referencedColumnName = "name")
	private Subject subjectOwner;
	
	Quiz() {}
	
	@Builder
	private Quiz(int number, String question, Set<QuizChoices> choices) {
		super();
		this.number = number;
		this.question = question;
		this.choices = choices;
	}

}
