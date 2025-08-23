package com.example.quizApp.mapper.section;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.quizApp.dto.section.AddSectionRequest;
import com.example.quizApp.dto.section.SectionDataResponse;
import com.example.quizApp.dto.section.UpdateSectionRequest;
import com.example.quizApp.model.teacher.sectionHandle.Section;

@Mapper
public interface SectionMapper {
	
	public final static SectionMapper INSTANCE = Mappers.getMapper(SectionMapper.class);
	
	public SectionDataResponse toDTO(Section section);
	
	public Section toEntity(SectionDataResponse sectionDTO);
	
	public Section toEntity(AddSectionRequest addSectionRequest);
	
	public Section toEntity(UpdateSectionRequest updateSectionRequest);
}
