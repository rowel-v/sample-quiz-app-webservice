package com.example.quizApp.repo.student.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizApp.model.student.account.StudentAccount;

@Repository
public interface StudentAccountRepo extends JpaRepository<StudentAccount, Long>{
	
	Optional<StudentAccount> findByUsername(String username);
	

}
