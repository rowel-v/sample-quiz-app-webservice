package com.example.quizApp.mapper.student;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.student.GetDataResponse;
import com.example.quizApp.dto.student.SaveIdentityRequest;
import com.example.quizApp.dto.student.UpdateIdentityRequest;
import com.example.quizApp.model.student.Student;
import com.example.quizApp.model.student.account.StudentAccount;

@Mapper
public interface StudentMapper {
	
	public static final StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
	
	@Mapping(target = "account", ignore = true)
	public Student toEntity(SaveIdentityRequest saveIdentityRequest);
	
	@Mapping(target = "account", ignore = true)
	public Student toEntity(UpdateIdentityRequest updateIdentityRequest);
	
	@Mapping(target = "account", expression = "java(accountToDto(student.getAccount()))")
	public GetDataResponse toGetDataResponse(Student student);
	
	default Map<String, String> accountToDto(StudentAccount account) {
		return Map.of("username", account.getUsername(), "password", "PROTECTED");
	}

}
