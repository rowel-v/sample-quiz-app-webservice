package com.example.quizApp.model.teacher.sectionHandle.subjectHandle;

import java.security.SecureRandom;
import java.util.Objects;

import org.springframework.util.Assert;

import com.example.quizApp.model.teacher.sectionHandle.Section;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "subject_handle", schema = "teacher_schema")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Getter @Setter
	private String name;

	@Getter @Setter @Column(name = "code")
	private String subjectCode;

	@Setter @Getter
	@ManyToOne
	@JoinColumn(name = "section_owner", referencedColumnName = "name")
	private Section sectionOwner;

	@PrePersist
	private void generateSubjectCode() {

		Assert.isNull(subjectCode, "subject code must be null");

		SecureRandom rand = new SecureRandom();

		final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		final int CHARACTERS_SIZE = CHARACTERS.length();

		final int CODE_LENGTH = 6;
		StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
		
		for (int i = 0; i < CODE_LENGTH; i++) {
			codeBuilder.append(CHARACTERS.charAt(rand.nextInt(CHARACTERS_SIZE)));
		}
		subjectCode = codeBuilder.toString();
	}

	Subject() {}

	@Builder
	private Subject(String name) {
		super();
		this.name = name;
	}


	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		return Objects.equals(name, other.name);
	}

}
