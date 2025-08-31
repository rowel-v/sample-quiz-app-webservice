package com.example.quizApp.repo.teacher.sectionHandle.subjectHandle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizApp.model.teacher.sectionHandle.subjectHandle.Subject;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer>{

}
