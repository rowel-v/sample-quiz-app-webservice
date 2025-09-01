package com.example.quizApp.mapper.task;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.quiz.AddQuizChoicesRequest;
import com.example.quizApp.dto.quiz.AddQuizRequest;
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle.Quiz;
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle.QuizAnswer;
import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle.QuizChoices;

@Mapper
public interface QuizMapper {
	
	public final static QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);
	
	@Mapping(target = "quizOwner", ignore = true)
	QuizChoices toEntity(AddQuizChoicesRequest req);
	
	Set<QuizChoices> toEntity(Set<AddQuizChoicesRequest> req);

	@Mapping(target = "quizOwner", ignore = true)
	QuizAnswer toEntity(Character answer);
	
    @Mapping(target = "choices", source = "choices")
	Quiz toEntity(AddQuizRequest request);

}
