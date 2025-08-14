package com.example.quizApp.controller.student;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizApp.dto.student.StudentDto;
import com.example.quizApp.service.result.Result.Save;
import com.example.quizApp.service.student.StudentService;

import lombok.RequiredArgsConstructor;

@RequestMapping("student")
@RestController @RequiredArgsConstructor
public class StudentController {
	
	private final StudentService studentService;
	
	@PostMapping("save")
	ResponseEntity<String> saveStudentIdentity(@RequestBody StudentDto studentDto) {
		
		Save result = studentService.saveIdentity(studentDto);
		
		return switch (result) {
		case SAVE_SUCCESS -> ResponseEntity.ok().build();
		case ALREADY_SAVE -> ResponseEntity.status(409).body("Already Save");
		};
	}
	
	@GetMapping("data")
	ResponseEntity<StudentDto> getStudentIdentity() {
		return ResponseEntity.ok(studentService.getIdentity());
	}
	
	@PutMapping("update")
	ResponseEntity<Void> updateStudentIdentity(@RequestBody StudentDto studentDto) {
		studentService.updateIdentity(studentDto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("account")
	ResponseEntity<Void> deleteStudentAccount() {
		studentService.deleteAccount();
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	ResponseEntity<List<StudentDto>> getAllStudentIdentity() {
		return ResponseEntity.ok(studentService.getAllIdentity());
	}
	
}
