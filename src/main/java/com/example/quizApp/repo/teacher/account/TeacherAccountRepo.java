package com.example.quizApp.repo.teacher.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizApp.model.teacher.account.TeacherAccount;

@Repository
public interface TeacherAccountRepo extends JpaRepository<TeacherAccount, Long> {
	
	Optional<TeacherAccount> findByUsername(String username);

}
