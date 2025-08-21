package com.example.quizApp.controller.teacher;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.teacher.TeacherDto;
import com.example.quizApp.result.shared.Result.Save;
import com.example.quizApp.service.teacher.TeacherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
@RequestMapping("teacher")
public class TeacherController {
	
	private final TeacherService teacherService;
	
	@PostMapping("save")
	ResponseEntity<Void> save(@RequestBody @Valid TeacherDto teacherDto) {
		Save res = teacherService.saveIdentity(teacherDto);
		return switch (res) {
		case ALREADY_SAVE -> ResponseEntity.status(409).build();
		case SAVE_SUCCESS ->  ResponseEntity.created(URI.create("teacher/data")).build();
		};
	}
	
	@PutMapping("update")
	ResponseEntity<Void> update(@RequestBody @Valid TeacherDto teacherDto) {
		teacherService.updateIdentity(teacherDto);
		return ResponseEntity.status(204).build();
	}
	
	@GetMapping("data")
	ResponseEntity<TeacherDto> get() {
		return ResponseEntity.ok(teacherService.getIdentity());
	}
	
	@DeleteMapping("account")
	ResponseEntity<Void> delete() {
		teacherService.deleteAccount();
		return ResponseEntity.status(204).build();
	}
}
