package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.quizSubmission.QuizSubmissionAnswerRequest;
import com.example.nhom3_tt_.dtos.response.quizSubmission.QuizSubmissionAnswerResponse;

public interface QuizSubmissionAnswerService {
  QuizSubmissionAnswerResponse create(QuizSubmissionAnswerRequest request);

  QuizSubmissionAnswerResponse getById(Long id);

  void deleteById(Long id);

  QuizSubmissionAnswerResponse update(Long id, QuizSubmissionAnswerRequest request);
}
