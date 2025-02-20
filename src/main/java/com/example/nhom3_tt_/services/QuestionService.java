package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.quiz.QuestionRequest;
import com.example.nhom3_tt_.dtos.response.quiz.QuestionResponse;
import com.example.nhom3_tt_.dtos.response.quiz.QuestionResponseNoAnswer;
import com.example.nhom3_tt_.models.Question;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuestionService {
  Question getQuestionEntityById(Long id);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  QuestionResponse create(QuestionRequest req);

  QuestionResponse getQuestionById(Long questionId);

  QuestionResponseNoAnswer getQuestionByIdNoAnswer(Long questionId);

  List<QuestionResponse> getAll();

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  QuestionResponse update(Long questionId, QuestionRequest req);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  void delete(Long questionId);

  List<QuestionResponse> getQuestionByQuizId(Long quizId);
}
