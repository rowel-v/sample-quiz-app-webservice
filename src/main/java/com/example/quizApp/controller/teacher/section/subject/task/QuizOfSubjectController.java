package com.example.quizApp.controller.teacher.section.subject.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.quiz.AddQuizRequest;
import com.example.quizApp.result.teacher.task.QuizResult.Add;
import com.example.quizApp.service.teacher.sectionHandle.subjectHandle.taskHandle.QuizService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
@RequestMapping("teacher/section")
public class QuizOfSubjectController {

	private final QuizService quizService;

	@PostMapping("{sectionName}/subject/{subjectName}/quiz")
	public ResponseEntity<String> addQuiz(@RequestBody @Valid AddQuizRequest request, 
			@PathVariable String sectionName, 
			@PathVariable String subjectName) {

		Add result = quizService.addQuiz(request, sectionName, subjectName);

		return switch (result) {
		case QUIZ_NUMBER_ALREADY_EXISTS, QUIZ_QUESTION_ALREADY_EXISTS, QUIZ_NUMBER_ALREADY_EXISTS_AND_QUIZ_QUESTION_ALREADY_EXISTS
		-> ResponseEntity.status(409).body(result.getInfo());
		case QUZ_ANSWER_NOT_ON_CHOICES -> ResponseEntity.status(400).body(result.getInfo());
		case QUIZ_ADDED_SUCCESS -> ResponseEntity.status(204).build();
		};
	}
}
