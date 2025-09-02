package com.example.quizApp.service.teacher.sectionHandle.subjectHandle.taskHandle;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quizApp.dto.quiz.AddQuizRequest;
import com.example.quizApp.dto.quiz.DeleteQuizRequest;
import com.example.quizApp.exception.SectionNotFoundException;
import com.example.quizApp.exception.SubjectNotFoundException;
import com.example.quizApp.exception.TeacherNotFoundException;
import com.example.quizApp.exception.UnauthorizedException;
import com.example.quizApp.mapper.task.QuizMapper;
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle.Quiz;
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle.QuizChoices;
import com.example.quizApp.repo.teacher.account.TeacherAccountRepo;
import com.example.quizApp.repo.teacher.sectionHandle.subjectHandle.taskHandle.QuizRepo;
import com.example.quizApp.result.teacher.task.QuizResult;
import com.example.quizApp.result.teacher.task.QuizResult.Add;
import com.example.quizApp.result.teacher.task.QuizResult.Delete;

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

		QuizResult.Add result;

		Quiz quizReq = QuizMapper.INSTANCE.toEntity(request);

		Integer quizNum = quizReq.getNumber();
		String quizQues = quizReq.getQuestion();
		Character quizAns = quizReq.getAnswer().get();

		boolean quizAnswerNotOnChoices = quizReq.getChoices().stream().map(QuizChoices::getLetter).noneMatch(quizAns::equals);

		if (quizAnswerNotOnChoices) {
			result = Add.QUZ_ANSWER_NOT_ON_CHOICES;
			result.setAnswer(quizAns).setQuizSubject(subjectOwnerName);
			return result;
		}

		var accountOwner = teacherAccountRepo.findByUsername(accountOwnerUsername.get())
				.orElseThrow(UnauthorizedException::new);

		var teacher = Optional.of(accountOwner.getTeacher()).orElseThrow(TeacherNotFoundException::new);

		var sectionOwner = teacher.getSections().stream().filter(s -> s.getName().equals(sectionOwnerName))
				.findFirst().orElseThrow(SectionNotFoundException::new);

		var subjectOwner = sectionOwner.getSubjects().stream()
				.filter(s -> s.getName().equals(subjectOwnerName))
				.findFirst().orElseThrow(SubjectNotFoundException::new);

		boolean quizNumberAlreadyExists = subjectOwner.getQuizHandle().stream()
				.mapToInt(Quiz::getNumber).anyMatch(quizNum::equals);

		boolean quizQuestionAlreadyExists = subjectOwner.getQuizHandle().stream()
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

	@Transactional
	public QuizResult.Delete removeQuiz(DeleteQuizRequest req) {

		var accountOwner = teacherAccountRepo.findByUsername(accountOwnerUsername.get())
				.orElseThrow(UnauthorizedException::new);

		var teacher = Optional.of(accountOwner.getTeacher()).orElseThrow(TeacherNotFoundException::new);

		var sectionOwner = teacher.getSections().stream()
				.filter(s -> s.getName().equals(req.getSectionName()))
				.findFirst()
				.orElseThrow(SectionNotFoundException::new);

		var subjectOwner = sectionOwner.getSubjects().stream()
				.filter(s -> s.getName().equals(req.getSubjectName()))
				.findFirst()
				.orElseThrow(SubjectNotFoundException::new);

		var quizFound = subjectOwner.getQuizHandle().stream()
				.mapToInt(Quiz::getNumber)
				.anyMatch(req.getQuizNumber()::equals);

		if (!quizFound) {
			QuizResult.Delete result = Delete.QUIZ_NUMBER_NOT_FOUND;
			result.setQuizNumber(req.getQuizNumber()).setQuizSubject(req.getSubjectName());
			return result;
		}

		quizRepo.deleteByNumberAndSubjectOwner(req.getQuizNumber(), subjectOwner);
		return Delete.QUIZ_DELETE_SUCCESS;
	}
}
