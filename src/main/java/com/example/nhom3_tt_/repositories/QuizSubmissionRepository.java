package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
  List<QuizSubmission> findAllByCreatedBy(Long studentId);

  List<QuizSubmission> findAllByQuizIdAndCreatedBy(Long quizId, Long studentId);

  List<QuizSubmission> findAllByQuizSectionCourseId(Long courseId);

  List<QuizSubmission> findAllByQuizId(Long quizId);
}
