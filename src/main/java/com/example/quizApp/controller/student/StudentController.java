package com.example.quizApp.controller.student;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.student.DataResponse;
import com.example.quizApp.dto.student.SaveIdentityRequest;
import com.example.quizApp.dto.student.UpdateIdentityRequest;
import com.example.quizApp.result.shared.Result.Save;
import com.example.quizApp.result.student.SaveSection;
import com.example.quizApp.service.student.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("student")
@RestController @RequiredArgsConstructor
public class StudentController {
	
	private final StudentService studentService;
	
	@PostMapping("save")
	ResponseEntity<String> saveStudent(@RequestBody @Valid SaveIdentityRequest saveIdentityRequest) {
		Save result = studentService.saveIdentity(saveIdentityRequest);
		return switch (result) {
		case SAVE_SUCCESS -> ResponseEntity.created(URI.create("student/data")).build();
		case ALREADY_SAVE -> ResponseEntity.status(409).body("Already Save");
		};
	}
	
	@GetMapping("data")
	ResponseEntity<DataResponse> getStudent() {
		return ResponseEntity.ok(studentService.getIdentity());
	}
	
	@PutMapping("update")
	ResponseEntity<Void> updateStudent(@RequestBody @Valid UpdateIdentityRequest updateIdentityRequest) {
		studentService.updateIdentity(updateIdentityRequest);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("account")
	ResponseEntity<Void> deleteStudentAccount() {
		studentService.deleteAccount();
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	ResponseEntity<List<DataResponse>> getAllStudent() {
		return ResponseEntity.ok(studentService.getAllIdentity());
	}
	
	@PostMapping("section/{sectionName}")
	ResponseEntity<Void> saveSection(@PathVariable String sectionName, @RequestParam String code) {
		SaveSection res = studentService.saveSection(sectionName, code);
		return switch (res) {
		case INVALID_SECTION_CODE -> ResponseEntity.status(404).build();
		case SECTION_NOT_FOUND -> ResponseEntity.status(404).build();
		case SUCCESS -> ResponseEntity.status(204).build();
 		};
	}	
}
