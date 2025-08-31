package com.example.quizApp.service.teacher.sectionHandle.subjectHandle.taskHandle;

import java.util.function.Supplier;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.quiz.AddQuizRequest;
import com.example.quizApp.exception.SectionNotFoundException;
import com.example.quizApp.exception.SubjectNotFoundException;
import com.example.quizApp.exception.TeacherNotFoundException;
import com.example.quizApp.exception.UnauthorizedException;
import com.example.quizApp.mapper.task.QuizMapper;
import com.example.quizApp.model.teacher.Teacher;
import com.example.quizApp.model.teacher.sectionHandle.Section;
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.Subject;
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle.Quiz;
import com.example.quizApp.repo.teacher.account.TeacherAccountRepo;
import com.example.quizApp.repo.teacher.sectionHandle.subjectHandle.taskHandle.QuizRepo;
import com.example.quizApp.result.teacher.task.QuizResult;
import com.example.quizApp.result.teacher.task.QuizResult.Add;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class QuizService {

	private final TeacherAccountRepo  teacherAccountRepo;
	private final QuizRepo quizRepo;

	private Supplier<String> accountOwnerUsername = () -> {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) return auth.getName();

		throw new UnauthorizedException();
	};

	public QuizResult.Add addQuiz(AddQuizRequest request, String sectionName, String subjectName) {
		return teacherAccountRepo.findByUsername(accountOwnerUsername.get())
				.map(acc -> {
					Teacher teacherOwner = acc.getTeacher();

					if (teacherOwner != null) {

						Section sectionOwner = teacherOwner.getSections().stream()
								.filter(sec -> sec.getName().equals(sectionName))
								.findFirst()
								.orElseThrow(() -> new SectionNotFoundException());

						Subject subjectOwner = sectionOwner.getSubjects().stream()
								.filter(sub -> sub.getName().equals(subjectName))
								.findFirst()
								.orElseThrow(() -> new SubjectNotFoundException());

						Quiz quizRequest = QuizMapper.INSTANCE.toEntity(request);
						
						var quizNumberAlreadyExists = subjectOwner.getQuizHandle().stream()
								.anyMatch(quiz -> quiz.getNumber() == quizRequest.getNumber());

						var quizQuestionAlreadyExists = subjectOwner.getQuizHandle().stream()
								.anyMatch(quiz -> quiz.getQuestion().equals(quizRequest.getQuestion()));

						QuizResult.Add result;

						if (quizNumberAlreadyExists && quizQuestionAlreadyExists) {
							result = Add.QUIZ_NUMBER_ALREADY_EXISTS_AND_QUIZ_QUESTION_ALREADY_EXISTS;
						}
						else if (quizNumberAlreadyExists) result = Add.QUIZ_NUMBER_ALREADY_EXISTS;
						else if (quizQuestionAlreadyExists) result = Add.QUIZ_QUESTION_ALREADY_EXISTS;
						else {
							subjectOwner.getQuizHandle().add(quizRequest);
							quizRequest.setSubjectOwner(subjectOwner);
							quizRequest.getChoices().forEach(c -> c.setQuizOwner(quizRequest));
							quizRepo.save(quizRequest);
							result = Add.QUIZ_ADDED_SUCCESS;
						}

						result.setQuizNumber(quizRequest.getNumber());
						result.setQuizQuestion(quizRequest.getQuestion());
						result.setQuizSubject(subjectName);
						return result;
					}
					throw new TeacherNotFoundException();
				})
				.orElseThrow(() -> new UnauthorizedException());
	}
}
