package com.example.quizApp.repo.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizApp.model.teacher.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {

}
