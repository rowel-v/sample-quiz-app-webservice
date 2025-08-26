package com.example.quizApp.mapper.student;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.student.DataResponse;
import com.example.quizApp.dto.student.SaveIdentityRequest;
import com.example.quizApp.dto.student.UpdateIdentityRequest;
import com.example.quizApp.model.student.Student;

@Mapper
public interface StudentMapper {
	
	public static final StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
	
	@Mapping(target = "account", ignore = true)
	public Student toEntity(SaveIdentityRequest saveIdentityRequest);
	
	@Mapping(target = "account", ignore = true)
	public Student toEntity(UpdateIdentityRequest updateIdentityRequest);

	@Mapping(target = "section.subject", source = "section.subjects")
	@Mapping(target = "account.password", constant = "PROTECTED")
	public DataResponse toDTO(Student student);

}
