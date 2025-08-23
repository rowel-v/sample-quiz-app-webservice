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

import com.example.quizApp.dto.teacher.SaveTeacherIdentityRequest;
import com.example.quizApp.dto.teacher.TeacherIdentityResponse;
import com.example.quizApp.dto.teacher.UpdateTeacherIdentityRequest;
import com.example.quizApp.result.shared.Result.Save;
import com.example.quizApp.service.teacher.TeacherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
@RequestMapping("teacher")
public class TeacherController {
	
	private final TeacherService teacherService;
	
	@PostMapping("save")
	ResponseEntity<Void> save(@RequestBody @Valid SaveTeacherIdentityRequest req) {
		Save res = teacherService.saveIdentity(req);
		return switch (res) {
		case ALREADY_SAVE -> ResponseEntity.status(409).build();
		case SAVE_SUCCESS ->  ResponseEntity.created(URI.create("teacher/data")).build();
		};
	}
	
	@PutMapping("update")
	ResponseEntity<Void> update(@RequestBody @Valid UpdateTeacherIdentityRequest req) {
		teacherService.updateIdentity(req);
		return ResponseEntity.status(204).build();
	}
	
	@GetMapping("data")
	ResponseEntity<TeacherIdentityResponse> get() {
		return ResponseEntity.ok(teacherService.getIdentity());
	}
	
	@DeleteMapping("account")
	ResponseEntity<Void> delete() {
		teacherService.deleteAccount();
		return ResponseEntity.status(204).build();
	}
}
