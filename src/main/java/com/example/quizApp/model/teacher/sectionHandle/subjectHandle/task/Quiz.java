package com.example.quizApp.model.teacher.sectionHandle.subjectHandle.task;

import java.util.List;

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
	
	private int quizNumber;
	private int questionSize;
	
	@OneToMany(mappedBy = "quizOwner")
	private List<Question> question;
	
	@Builder
	private Quiz(int quizNumber, int questionSize) {
		super();
		this.quizNumber = quizNumber;
		this.questionSize = questionSize;
	}

}
