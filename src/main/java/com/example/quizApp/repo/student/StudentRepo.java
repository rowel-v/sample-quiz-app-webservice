package com.example.quizApp.repo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizApp.model.student.StudentIdentity;

@Repository
public interface StudentRepo extends JpaRepository<StudentIdentity, Long> {
	
}
	