package com.example.quizApp.mapper.teacher;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.teacher.TeacherIdentityResponse;
import com.example.quizApp.dto.teacher.UpdateTeacherIdentityRequest;
import com.example.quizApp.model.teacher.Teacher;
import com.example.quizApp.model.teacher.account.TeacherAccount;

@Mapper
public interface TeacherMapper {
	
	public static final TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);
	
	@Mapping(target = "account", ignore = true)
	public Teacher toEntity(UpdateTeacherIdentityRequest request);
	
	@Mapping(target = "handledSection", source = "sections")
	@Mapping(target = "account", expression = "java(accountToDTO(teacher.getAccount()))")
	public TeacherIdentityResponse toDto(Teacher teacher);
	
	default Map<String, String> accountToDTO(TeacherAccount acc) {
		return Map.of("username", acc.getUsername(), "password", "PROTECTED");
	}
}
