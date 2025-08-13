package com.example.quizApp.mapper.student;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.student.StudentDto;
import com.example.quizApp.model.student.StudentIdentity;

@Mapper
public interface StudentMapper {
	
	public static final StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
	
	@Mapping(target = "account.createdAt", ignore = true)
	public StudentIdentity toEntity(StudentDto studentDto);
	
	@Mapping(target = "account.password", constant = "PROTECTED")
	public StudentDto tDto(StudentIdentity studentIdentity);

}
