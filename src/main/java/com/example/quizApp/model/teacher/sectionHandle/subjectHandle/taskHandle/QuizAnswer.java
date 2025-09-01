package com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity @Table(name = "quiz_answer", schema = "teacher_schema")
public class QuizAnswer {
	
	@Setter(AccessLevel.NONE) @Getter
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Character answer;
	
	public void set(Character answer) {
		this.answer = answer;
	}
	
	public Character get() {
		return answer;
	}
	
	@OneToOne() @Getter @Setter
	@JoinColumn(name = "quiz_id")
	private Quiz quizOwner;

	@Builder
	private QuizAnswer(char answer, Quiz quizOwner) {
		this.answer = answer;
		this.quizOwner = quizOwner;
	}
}
