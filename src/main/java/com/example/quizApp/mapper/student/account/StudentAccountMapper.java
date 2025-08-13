package com.example.quizApp.mapper.student.account;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.student.account.StudentAccountDto;
import com.example.quizApp.model.student.account.StudentAccount;

@Mapper
public interface StudentAccountMapper {
	
	public final static StudentAccountMapper INSTANCE = Mappers.getMapper(StudentAccountMapper.class);
	
	StudentAccount toEntity(StudentAccountDto sDto);
}
