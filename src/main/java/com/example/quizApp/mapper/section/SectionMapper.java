package com.example.quizApp.mapper.section;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.section.SectionDTO;
import com.example.quizApp.model.teacher.sectionHandle.Section;

@Mapper
public interface SectionMapper {
	
	public final static SectionMapper INSTANCE = Mappers.getMapper(SectionMapper.class);
	
	public Section toEntity(SectionDTO sectionDTO);
	
	public SectionDTO toDTO(Section section);

}
