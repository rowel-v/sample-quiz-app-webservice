package com.example.quizApp.repo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizApp.model.student.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
	
	void deleteByFullname(String fullname);
	
}
	