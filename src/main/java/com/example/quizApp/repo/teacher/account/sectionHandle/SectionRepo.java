package com.example.quizApp.repo.teacher.account.sectionHandle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizApp.model.teacher.sectionHandled.Section;

@Repository
public interface SectionRepo extends JpaRepository<Section, Long> {

}
