package com.example.quizApp.controller.teacher;

import java.net.URI;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.section.AddSectionRequest;
import com.example.quizApp.dto.section.SectionDataResponse;
import com.example.quizApp.dto.section.UpdateSectionRequest;
import com.example.quizApp.result.teacher.section.SectionResult;
import com.example.quizApp.service.teacher.sectionHandle.SectionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
@RequestMapping("teacher/section")
public class SectionHandleController {

	private final SectionService sectionService;
	
	@PostMapping
	ResponseEntity<Void> addSection(@RequestBody @Valid AddSectionRequest req) {
		SectionResult.Add res = sectionService.addSection(req);
		return switch (res) {
		case SECTION_ALREADY_ADDED -> ResponseEntity.status(409).build();
		case SUCCESS -> ResponseEntity.created(URI.create("teacher/section")).build();
		};
	}
	
	@GetMapping
	ResponseEntity<Set<SectionDataResponse>> getSections() {
		return ResponseEntity.ok(sectionService.getSectionsHandled());
	}
	
	@PutMapping("{sectionNameToUpdate}")
	ResponseEntity<?> updateSection(@PathVariable String sectionNameToUpdate, @RequestBody UpdateSectionRequest req) {
		SectionResult.Update res = sectionService.updateSection(sectionNameToUpdate, req);
		return switch (res) {
		case SECTION_NOT_FOUND -> ResponseEntity.status(404).build();
		case SECTION_STILL_SAME -> ResponseEntity.ok("Section still same");
		case SECTION_ALREADY_EXISTS -> ResponseEntity.status(409).build();
		case SUCCESS -> ResponseEntity.status(204).build();
		};
	}
	
	@DeleteMapping("{sectionName}")
	ResponseEntity<Void> deleteSection(@PathVariable String sectionName) {
		SectionResult.Delete res = sectionService.deleteSection(sectionName);
		return switch (res) {
		case SECTION_NOT_FOUND -> ResponseEntity.status(404).build();
		case SUCCESS -> ResponseEntity.status(204).build();
		};
	}
}
