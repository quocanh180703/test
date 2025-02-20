package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.quizSubmission.QuizSubmissionRequest;
import com.example.nhom3_tt_.dtos.response.quiz.QuestionResponse;
import com.example.nhom3_tt_.dtos.response.quizSubmission.CustomAnswerResponse;
import com.example.nhom3_tt_.dtos.response.quizSubmission.QuizSubmissionResponse;
import com.example.nhom3_tt_.exception.CustomException;
import com.example.nhom3_tt_.models.Quiz;
import com.example.nhom3_tt_.models.QuizSubmission;
import com.example.nhom3_tt_.models.QuizSubmissionAnswer;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.QuizSubmissionAnswerRepository;
import com.example.nhom3_tt_.repositories.QuizSubmissionRepository;
import com.example.nhom3_tt_.services.EnrollService;
import com.example.nhom3_tt_.services.QuestionService;
import com.example.nhom3_tt_.services.QuizService;
import com.example.nhom3_tt_.services.QuizSubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizSubmissionServiceImpl implements QuizSubmissionService {

  private final QuizSubmissionRepository repository;
  private final QuizSubmissionAnswerRepository quizSubmissionAnswerRepository;
  private final QuestionService questionService;
  private final QuizService quizService;
  private final EnrollService enrollService;

  @Override
  public QuizSubmissionResponse getById(Long id) {
    QuizSubmission quizSubmission = repository.findById(id).get();

    return QuizSubmissionResponse.builder()
        .id(quizSubmission.getId())
        .quizId(quizSubmission.getQuiz().getId())
        .quizTitle(quizSubmission.getQuiz().getTitle())
        .score(quizSubmission.getScore())
        .totalTimes(quizSubmission.getTotalTimes())
        .totalCorrects(quizSubmission.getTotalCorrects())
        .totalQuestions(quizSubmission.getQuiz().getQuestions().size())
        .answers(toCustomAnswerResponse(quizSubmission))
        .build();
  }

  @Override
  public QuizSubmissionResponse create(QuizSubmissionRequest request) {
    Long userId =
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

    Quiz quiz = quizService.getQuizEntityById(request.getQuizId());
    if (!enrollService.isEnrolled(userId, quiz.getSection().getCourse().getId())) {
      throw new CustomException("You are not enrolled in this course", 400);
    }
    if (quiz.getAttemptAllowed()
        <= repository.findAllByQuizIdAndCreatedBy(request.getQuizId(), userId).size()) {
      throw new CustomException("You have reached the maximum number of attempts", 400);
    }

    if (request.getTotalTimes() <= 0 || request.getTotalTimes() > quiz.getTimeLimit()) {
      throw new CustomException(
          "Total times must be greater than 0 and less than the quiz time limit ("
              + quiz.getTimeLimit()
              + ")",
          400);
    }

    QuizSubmission quizSubmission =
        QuizSubmission.builder()
            .quiz(quizService.getQuizEntityById(request.getQuizId()))
            .score(0D)
            .totalTimes(request.getTotalTimes())
            .totalCorrects(0)
            .build();
    quizSubmission = repository.save(quizSubmission);
    List<QuizSubmissionAnswer> quizSubmissionAnswers = new ArrayList<>();
    AtomicInteger finalTotalCorrects = new AtomicInteger(0);
    Double score = 0D;
    if (request.getAnswers() != null) {
      QuizSubmission finalQuizSubmission = quizSubmission;
      request
          .getAnswers()
          .forEach(
              answer -> {
                QuizSubmissionAnswer quizSubmissionAnswer =
                    QuizSubmissionAnswer.builder()
                        .quizSubmission(finalQuizSubmission)
                        .question(questionService.getQuestionEntityById(answer.getQuestionId()))
                        .answer(answer.getAnswer())
                        .build();
                quizSubmissionAnswerRepository.save(quizSubmissionAnswer);
                quizSubmissionAnswers.add(quizSubmissionAnswer);
              });
    }
    quizSubmission.setAnswers(quizSubmissionAnswers);

    getById(quizSubmission.getId()).getAnswers().stream()
        .filter(CustomAnswerResponse::isCorrect)
        .forEach(answer -> finalTotalCorrects.getAndIncrement());
    quizSubmission.setTotalCorrects(finalTotalCorrects.get());
    int totalQuestions =
        quizService.getQuizEntityById(quizSubmission.getQuiz().getId()).getQuestions().size();
    score = (double) finalTotalCorrects.get() / totalQuestions * 100;
    quizSubmission.setScore(score);
    return getById(repository.save(quizSubmission).getId());
  }

  @Override
  public List<QuizSubmissionResponse> getByQuizId(Long quizId) {
    List<QuizSubmission> quizSubmissionList = repository.findAllByQuizId(quizId);
    return quizSubmissionList.stream()
        .map(
            quizSubmission ->
                QuizSubmissionResponse.builder()
                    .id(quizSubmission.getId())
                    .quizId(quizSubmission.getQuiz().getId())
                    .quizTitle(quizSubmission.getQuiz().getTitle())
                    .score(quizSubmission.getScore())
                    .totalTimes(quizSubmission.getTotalTimes())
                    .totalCorrects(quizSubmission.getTotalCorrects())
                    .answers(toCustomAnswerResponse(quizSubmission))
                    .totalQuestions(quizSubmission.getQuiz().getQuestions().size())
                    .build())
        .toList();
  }

  @Override
  public List<QuizSubmissionResponse> getByCourseId(Long courseId) {
    List<QuizSubmission> quizSubmissionList = repository.findAllByQuizSectionCourseId(courseId);
    return quizSubmissionList.stream()
        .map(
            quizSubmission ->
                QuizSubmissionResponse.builder()
                    .id(quizSubmission.getId())
                    .quizId(quizSubmission.getQuiz().getId())
                    .quizTitle(quizSubmission.getQuiz().getTitle())
                    .score(quizSubmission.getScore())
                    .totalTimes(quizSubmission.getTotalTimes())
                    .totalCorrects(quizSubmission.getTotalCorrects())
                    .answers(toCustomAnswerResponse(quizSubmission))
                    .totalQuestions(quizSubmission.getQuiz().getQuestions().size())
                    .build())
        .toList();
  }

  @Override
  public List<QuizSubmissionResponse> getByStudentId(Long studentId) {
    List<QuizSubmission> quizSubmissionList = repository.findAllByCreatedBy(studentId);
    return quizSubmissionList.stream()
        .map(
            quizSubmission ->
                QuizSubmissionResponse.builder()
                    .id(quizSubmission.getId())
                    .quizId(quizSubmission.getQuiz().getId())
                    .quizTitle(quizSubmission.getQuiz().getTitle())
                    .score(quizSubmission.getScore())
                    .totalTimes(quizSubmission.getTotalTimes())
                    .totalCorrects(quizSubmission.getTotalCorrects())
                    .totalQuestions(quizSubmission.getQuiz().getQuestions().size())
                    .answers(toCustomAnswerResponse(quizSubmission))
                    .build())
        .toList();
  }

  @Override
  public List<QuizSubmissionResponse> getByLoggedInUser() {
    Long userId =
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    return getByStudentId(userId);
  }

  @Override
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public List<CustomAnswerResponse> toCustomAnswerResponse(QuizSubmission quizSubmission) {
    return quizSubmission.getAnswers().stream()
        .map(
            answer -> {
              QuestionResponse question =
                  questionService.getQuestionById(answer.getQuestion().getId());
              String answerContent =
                  question.getOptions().stream()
                      .filter(option -> option.getId().equals(answer.getAnswer()))
                      .findFirst()
                      .get()
                      .getContent();
              String correctAnswerContent =
                  question.getOptions().stream()
                      .filter(option -> option.getId().equals(question.getAnswer()))
                      .findFirst()
                      .get()
                      .getContent();
              boolean isCorrect = answer.getAnswer().equals(question.getAnswer());
              return CustomAnswerResponse.builder()
                  .id(answer.getId())
                  .quizSubmissionId(answer.getQuizSubmission().getId())
                  .questionId(answer.getQuestion().getId())
                  .questionContent(answer.getQuestion().getContent())
                  .answerContent(answerContent)
                  .correctAnswerContent(correctAnswerContent)
                  .isCorrect(isCorrect)
                  .build();
            })
        .toList();
  }
}
