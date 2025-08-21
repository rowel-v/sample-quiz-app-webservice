package com.example.quizApp.mapper.teacher;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;import com.example.quizApp.dto.teacher.TeacherDto;
import com.example.quizApp.model.teacher.Teacher;

@Mapper
public interface TeacherMapper {
	
	public static final TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);
	
	@Mapping(target = "account.teacher", ignore = true)
	public Teacher toEntity(TeacherDto teacherDto);
	
	@Mapping(target = "account.password", constant = "PROTECTED")
	@Mapping(target = "handledSection", source = "sections")
	public TeacherDto toDto(Teacher teacher);

}
