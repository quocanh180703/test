package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.quiz.QuizRequest;
import com.example.nhom3_tt_.dtos.response.quiz.QuizResponse;
import com.example.nhom3_tt_.dtos.response.quiz.QuizResponseWithQuestion;
import com.example.nhom3_tt_.models.Quiz;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuizService {

  Quiz getQuizEntityById(Long id);

  QuizResponse getQuizById(Long id);

  QuizResponseWithQuestion getQuizQuestionById(Long id);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  QuizResponse create(QuizRequest quizRequest);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  QuizResponse update(Long id, QuizRequest quizRequest);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  void delete(Long id);

  List<QuizResponse> getAll();

  void importQuizUsingExcel(MultipartFile questions, Long quizId) throws IOException;
}
