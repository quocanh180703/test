package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.quizSubmission.QuizSubmissionAnswerRequest;
import com.example.nhom3_tt_.dtos.response.quizSubmission.QuizSubmissionAnswerResponse;
import com.example.nhom3_tt_.exception.NotFoundException;
import com.example.nhom3_tt_.models.QuizSubmissionAnswer;
import com.example.nhom3_tt_.repositories.QuestionRepository;
import com.example.nhom3_tt_.repositories.QuizSubmissionAnswerRepository;
import com.example.nhom3_tt_.repositories.QuizSubmissionRepository;
import com.example.nhom3_tt_.services.QuizSubmissionAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizSubmissionAnswerServiceImpl implements QuizSubmissionAnswerService {
  private final QuizSubmissionAnswerRepository repository;
  private final QuizSubmissionRepository quizSubmissionRepository;
  private final QuestionRepository questionRepository;

  @Override
  public QuizSubmissionAnswerResponse create(QuizSubmissionAnswerRequest request) {
    QuizSubmissionAnswer quizSubmissionAnswer =
        QuizSubmissionAnswer.builder()
            .quizSubmission(
                quizSubmissionRepository
                    .findById(request.getQuizSubmissionId())
                    .orElseThrow(() -> new NotFoundException("QuizSubmission cannot found!")))
            .question(
                questionRepository
                    .findById(request.getQuestionId())
                    .orElseThrow(() -> new NotFoundException("Question cannot found!")))
            .answer(request.getAnswer())
            .build();
    quizSubmissionAnswer = repository.save(quizSubmissionAnswer);
    return QuizSubmissionAnswerResponse.builder()
        .id(quizSubmissionAnswer.getId())
        .quizSubmissionId(quizSubmissionAnswer.getQuizSubmission().getId())
        .questionId(quizSubmissionAnswer.getQuestion().getId())
        .questionContent(quizSubmissionAnswer.getQuestion().getContent())
        .answer(quizSubmissionAnswer.getAnswer())
        .build();
  }

  @Override
  public QuizSubmissionAnswerResponse getById(Long id) {
    QuizSubmissionAnswer quizSubmissionAnswer =
        repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("QuizSubmissionAnswer cannot found"));
    return QuizSubmissionAnswerResponse.builder()
        .id(quizSubmissionAnswer.getId())
        .quizSubmissionId(quizSubmissionAnswer.getQuizSubmission().getId())
        .questionId(quizSubmissionAnswer.getQuestion().getId())
        .questionContent(quizSubmissionAnswer.getQuestion().getContent())
        .answer(quizSubmissionAnswer.getAnswer())
        .build();
  }

  @Override
  public void deleteById(Long id) {
    if (!repository.existsById(id)) {
      throw new NotFoundException("QuizSubmissionAnswer not found with id: " + id);
    }
    repository.deleteById(id);
  }

  @Override
  public QuizSubmissionAnswerResponse update(Long id, QuizSubmissionAnswerRequest request) {
    QuizSubmissionAnswer quizSubmissionAnswer =
        repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("QuizSubmissionAnswer cannot found"));
    quizSubmissionAnswer.setAnswer(request.getAnswer());
    quizSubmissionAnswer = repository.save(quizSubmissionAnswer);
    return QuizSubmissionAnswerResponse.builder()
        .id(quizSubmissionAnswer.getId())
        .quizSubmissionId(quizSubmissionAnswer.getQuizSubmission().getId())
        .questionId(quizSubmissionAnswer.getQuestion().getId())
        .questionContent(quizSubmissionAnswer.getQuestion().getContent())
        .answer(quizSubmissionAnswer.getAnswer())
        .build();
  }
}
