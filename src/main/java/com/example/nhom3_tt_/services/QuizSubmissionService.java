package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.quizSubmission.QuizSubmissionRequest;
import com.example.nhom3_tt_.dtos.response.quizSubmission.QuizSubmissionResponse;
import com.example.nhom3_tt_.models.QuizSubmission;

import java.util.List;

public interface QuizSubmissionService {
  QuizSubmissionResponse getById(Long id);

  QuizSubmissionResponse create(QuizSubmissionRequest request);

  List<QuizSubmissionResponse> getByQuizId(Long quizId);

  List<QuizSubmissionResponse> getByCourseId(Long courseId);

  List<QuizSubmissionResponse> getByStudentId(Long studentId);

  List<QuizSubmissionResponse> getByLoggedInUser();

  void deleteById(Long id);
}
