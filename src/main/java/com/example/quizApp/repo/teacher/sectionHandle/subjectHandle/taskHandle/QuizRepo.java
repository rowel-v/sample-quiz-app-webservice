package com.example.quizApp.repo.teacher.sectionHandle.subjectHandle.taskHandle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.taskHandle.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer>{

}
