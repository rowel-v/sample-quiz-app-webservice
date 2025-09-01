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
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle.QuizChoices;
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

	public QuizResult.Add addQuiz(AddQuizRequest request, String sectionOwnerName, String subjectOwnerName) {
		return teacherAccountRepo.findByUsername(accountOwnerUsername.get())
				.map(acc -> {
					Teacher teacherOwner = acc.getTeacher();

					if (teacherOwner != null) {

						Section sectionOwner = teacherOwner.getSections().stream()
								.filter(sec -> sec.getName().equals(sectionOwnerName))
								.findFirst()
								.orElseThrow(() -> new SectionNotFoundException());

						Subject subjectOwner = sectionOwner.getSubjects().stream()
								.filter(sub -> sub.getName().equals(subjectOwnerName))
								.findFirst()
								.orElseThrow(() -> new SubjectNotFoundException());

						Quiz quizReq = QuizMapper.INSTANCE.toEntity(request);
						
						var quizNum = quizReq.getNumber();
						var quizQues = quizReq.getQuestion();
						Character quizAns = quizReq.getAnswer().get();

						boolean quizAnswerNotOnChoices = quizReq.getChoices().stream()
								.map(QuizChoices::getLetter)
								.noneMatch(quizAns::equals);
						
						QuizResult.Add result;
						
						if (quizAnswerNotOnChoices) {
							result = Add.QUZ_ANSWER_NOT_ON_CHOICES;
							result.setAnswer(quizAns);
							result.setQuizSubject(subjectOwnerName);
							return result;
						}

						var quizNumberAlreadyExists = subjectOwner.getQuizHandle().stream()
								.mapToInt(Quiz::getNumber).anyMatch(v -> v == quizNum);

						var quizQuestionAlreadyExists = subjectOwner.getQuizHandle().stream()
								.map(Quiz::getQuestion).anyMatch(quizQues::equals);

						if (quizNumberAlreadyExists && quizQuestionAlreadyExists) {
							result = Add.QUIZ_NUMBER_ALREADY_EXISTS_AND_QUIZ_QUESTION_ALREADY_EXISTS;
						}
						else if (quizNumberAlreadyExists) result = Add.QUIZ_NUMBER_ALREADY_EXISTS;
						else if (quizQuestionAlreadyExists) result = Add.QUIZ_QUESTION_ALREADY_EXISTS;
						else {
							subjectOwner.getQuizHandle().add(quizReq);
							quizReq.setSubjectOwner(subjectOwner);
							quizReq.getChoices().forEach(c -> c.setQuizOwner(quizReq));
							quizReq.getAnswer().setQuizOwner(quizReq);
							quizRepo.save(quizReq);
							result = Add.QUIZ_ADDED_SUCCESS;
						}

						result.setQuizNumber(quizNum);
						result.setQuizQuestion(quizQues);
						result.setQuizSubject(subjectOwnerName);
						return result;
					}
					throw new TeacherNotFoundException();
				})
				.orElseThrow(() -> new UnauthorizedException());
	}
}
