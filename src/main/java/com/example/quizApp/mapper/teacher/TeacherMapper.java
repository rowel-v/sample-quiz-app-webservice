package com.example.quizApp.mapper.teacher;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;import com.example.quizApp.dto.teacher.TeacherDto;
import com.example.quizApp.model.teacher.Teacher;

@Mapper
public interface TeacherMapper {
	
	public static final TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);
	
	public Teacher toEntity(TeacherDto teacherDto);

}
