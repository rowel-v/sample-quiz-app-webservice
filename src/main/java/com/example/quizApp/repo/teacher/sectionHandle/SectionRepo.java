package com.example.quizApp.repo.teacher.sectionHandle;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizApp.model.teacher.sectionHandle.Section;

@Repository
public interface SectionRepo extends JpaRepository<Section, Long> {
	Optional<Section> findByName(String name);
	boolean existsBySectionCode(String sectionCode);
	boolean existsByName(String name);
}
