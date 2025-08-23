package com.example.quizApp.mapper.teacher.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.shared.SignupRequestDTO;
import com.example.quizApp.model.teacher.account.TeacherAccount;

@Mapper
public interface TeacherAccountMapper {
	
	public static final TeacherAccountMapper INSTANCE = Mappers.getMapper(TeacherAccountMapper.class);
	
	@Mapping(target = "teacher", ignore = true)
	public TeacherAccount toEntity(SignupRequestDTO signupRequest);
}
