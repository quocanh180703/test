package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.QuizSubmissionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizSubmissionAnswerRepository extends JpaRepository<QuizSubmissionAnswer, Long> {}
