package com.example.quizApp.service.student;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.quizApp.dto.student.DataResponse;
import com.example.quizApp.dto.student.SaveIdentityRequest;
import com.example.quizApp.dto.student.UpdateIdentityRequest;
import com.example.quizApp.exception.StudentNotFoundException;
import com.example.quizApp.exception.UnauthorizedException;
import com.example.quizApp.mapper.student.StudentMapper;
import com.example.quizApp.model.student.Student;
import com.example.quizApp.repo.student.StudentRepo;
import com.example.quizApp.repo.student.account.StudentAccountRepo;
import com.example.quizApp.repo.teacher.sectionHandle.SectionRepo;
import com.example.quizApp.result.shared.Result;
import com.example.quizApp.result.shared.Result.Save;
import com.example.quizApp.result.student.SaveSection;
import com.example.quizApp.security.service.student.StudentDetails;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class StudentService {

	private final StudentAccountRepo studentAccountRepo;
	private final StudentRepo studentRepo;

	private Supplier<String> studentUsername = () -> SecurityContextHolder.getContext().getAuthentication().getName();
	private Supplier<StudentDetails> studentDetails = () -> {

		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) throw new UnauthorizedException();

		return (StudentDetails) auth.getPrincipal();
	};

	// student can be save one time only, it can be update in Result.Update
	public Result.Save saveIdentity(SaveIdentityRequest saveIdentityRequest) {
		Student student = StudentMapper.INSTANCE.toEntity(saveIdentityRequest);

		var username = this.studentUsername.get();
		var account = studentAccountRepo.findByUsername(username).get();

		if (account.getStudent() != null) return Save.ALREADY_SAVE;

		student.setAccount(account);
		studentRepo.save(student);
		return Save.SAVE_SUCCESS;
	}

	public DataResponse getIdentity() {
		return studentAccountRepo.findByUsername(studentUsername.get())
				.map(acc -> StudentMapper.INSTANCE.toDTO(acc.getStudent()))
				.orElseThrow(() -> new StudentNotFoundException());	
	}

	public void updateIdentity(UpdateIdentityRequest request) {
		studentAccountRepo.findByUsername(studentUsername.get()).ifPresent(acc -> {
			Student student = StudentMapper.INSTANCE.toEntity(request);
			// check the request data if not equal in a persisted entity (i.e., firstname, lastname & fullname)
			if (acc.getStudent() == null) throw new StudentNotFoundException();
			if (!acc.getStudent().equals(student)) {
				acc.getStudent().setFirstname(request.getFirstname());
				acc.getStudent().setLastname(request.getLastname());
				studentAccountRepo.save(acc);
			}
		});
	}

	@Transactional
	public void deleteAccount() {	
		studentAccountRepo.deleteById(studentDetails.get().getAccountId());
	}

	public List<DataResponse> getAllIdentity() {	
		return studentRepo.findAll()
				.stream()
				.map(StudentMapper.INSTANCE::toDTO)
				.collect(Collectors.toList());
	}

	private final SectionRepo sectionRepo;

	public SaveSection saveSection(String sectionName, String sectionCode) {
		return studentAccountRepo.findByUsername(studentUsername.get())
				.map(acc -> {
					Student student = acc.getStudent();
					if (student == null) throw new StudentNotFoundException();

					return sectionRepo.findByName(sectionName)
							.map(section -> {
								var validCode = section.getSectionCode().equals(sectionCode);
								if (!validCode) return SaveSection.INVALID_SECTION_CODE;

								student.setSection(section.getName());
								studentRepo.save(student);
								return SaveSection.SUCCESS;
							})
							.orElse(SaveSection.SECTION_NOT_FOUND);
				})
				.orElseThrow(() -> new UnauthorizedException());
	}
}
