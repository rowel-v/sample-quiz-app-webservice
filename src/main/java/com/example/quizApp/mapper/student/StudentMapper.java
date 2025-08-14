package com.example.quizApp.mapper.student;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.student.StudentDto;
import com.example.quizApp.model.student.Student;

@Mapper
public interface StudentMapper {
	
	public static final StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
	
	public Student toEntity(StudentDto studentDto);
	
	@Mapping(target = "account.password", constant = "PROTECTED")
	public StudentDto toDto(Student student);

}
