package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.quiz.QuestionRequest;
import com.example.nhom3_tt_.dtos.response.quiz.QuestionOptionResponse;
import com.example.nhom3_tt_.dtos.response.quiz.QuestionResponse;
import com.example.nhom3_tt_.dtos.response.quiz.QuestionResponseNoAnswer;
import com.example.nhom3_tt_.models.Question;
import com.example.nhom3_tt_.models.QuestionOption;
import com.example.nhom3_tt_.models.embeddedId.QuestionOptionId;
import com.example.nhom3_tt_.repositories.QuestionOptionRepository;
import com.example.nhom3_tt_.repositories.QuestionRepository;
import com.example.nhom3_tt_.repositories.QuizRepository;
import com.example.nhom3_tt_.services.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {
  private final QuestionRepository questionRepository;
  private final QuestionOptionRepository questionOptionRepository;
  private final QuizRepository quizRepository;

  @Override
  public Question getQuestionEntityById(Long id) {
    return questionRepository.findById(id).orElse(null);
  }

  @Override
  public QuestionResponse create(QuestionRequest req) {
    Question question =
        Question.builder()
            .quiz(quizRepository.findById(req.getQuizId()).orElse(null))
            .content(req.getContent())
            .build();
    Question savedQuestion = questionRepository.save(question);
    List<String> options = req.getOptions();
    QuestionResponse response =
        QuestionResponse.builder()
            .quizId(question.getQuiz().getId())
            .options(new ArrayList<>())
            .content(question.getContent())
            .build();
    log.info("Options: {}", options);
    log.info("QUESTION ID::: {}", question.getId());
    AtomicInteger seq = new AtomicInteger(1);
    options.forEach(
        option -> {
          QuestionOptionId id = new QuestionOptionId();
          id.setQuestionId(savedQuestion.getId());
          id.setId(System.currentTimeMillis()); // Use current time in milliseconds as a unique ID
          QuestionOption questionOption =
              QuestionOption.builder()
                  .id(id)
                  .content(option)
                  .question(question)
                  .seq(seq.getAndIncrement())
                  .build();

          questionOptionRepository.save(questionOption);
          response
              .getOptions()
              .add(
                  new QuestionOptionResponse(
                      questionOption.getId().getId(),
                      questionOption.getId().getQuestionId(),
                      questionOption.getContent()));
          if (response.getOptions().size() == req.getAnswer()) {
            savedQuestion.setAnswer(questionOption.getId().getId());
            questionRepository.save(savedQuestion);
          }
        });
    response.setAnswer(savedQuestion.getAnswer());
    return response;
  }

  @Override
  public QuestionResponse update(Long questionId, QuestionRequest req) {
    Question question = questionRepository.findById(questionId).orElse(null);
    if (question == null) {
      return null;
    }
    question.setContent(req.getContent() == null ? question.getContent() : req.getContent());
    question.setAnswer(req.getAnswer() == null ? question.getAnswer() : req.getAnswer());

    log.info("QUIZ ID: {}", req.getQuizId());
    if (req.getQuizId() != null) {
      question.setQuiz(quizRepository.findById(req.getQuizId()).orElse(question.getQuiz()));
    }

    questionRepository.save(question);

    List<String> options = req.getOptions();
    QuestionResponse response =
        QuestionResponse.builder()
            .quizId(question.getQuiz().getId())
            .answer(question.getAnswer())
            .options(new ArrayList<>())
            .content(question.getContent())
            .build();

    List<QuestionOption> questionOptions = questionOptionRepository.findAllByQuestionId(questionId);
    if (options != null) {
      questionOptionRepository.deleteAll(questionOptions);

      options.forEach(
          option -> {
            QuestionOptionId id = new QuestionOptionId();
            id.setQuestionId(question.getId());
            id.setId(System.currentTimeMillis()); // Use current time in milliseconds as a unique ID
            QuestionOption questionOption =
                QuestionOption.builder().id(id).content(option).question(question).build();
            questionOptionRepository.save(questionOption);
            response
                .getOptions()
                .add(
                    new QuestionOptionResponse(
                        questionOption.getId().getId(),
                        questionOption.getId().getQuestionId(),
                        questionOption.getContent()));
            if (response.getOptions().size() == req.getAnswer()) {
              question.setAnswer(questionOption.getId().getId());
              questionRepository.save(question);
            }
          });
    } else {
      response.setOptions(
          questionOptions.stream()
              .map(
                  option ->
                      new QuestionOptionResponse(
                          option.getId().getId(),
                          option.getId().getQuestionId(),
                          option.getContent()))
              .toList());
    }
    response.setAnswer(question.getAnswer());
    return response;
  }

  @Override
  public QuestionResponse getQuestionById(Long questionId) {
    Question question = questionRepository.findById(questionId).orElse(null);
    if (question == null) {
      return null;
    }
    List<QuestionOption> questionOptions = questionOptionRepository.findAllByQuestionId(questionId);
    List<QuestionOptionResponse> questionOptionResponses = new ArrayList<>();
    questionOptions.forEach(
        questionOption -> {
          questionOptionResponses.add(
              new QuestionOptionResponse(
                  questionOption.getId().getId(),
                  questionOption.getId().getQuestionId(),
                  questionOption.getContent()));
        });
    return QuestionResponse.builder()
        .content(question.getContent())
        .options(questionOptionResponses)
        .answer(question.getAnswer())
        .quizId(question.getQuiz().getId())
        .build();
  }

  @Override
  public QuestionResponseNoAnswer getQuestionByIdNoAnswer(Long questionId) {
    Question question = questionRepository.findById(questionId).orElse(null);
    if (question == null) {
      return null;
    }
    List<QuestionOption> questionOptions = questionOptionRepository.findAllByQuestionId(questionId);
    List<QuestionOptionResponse> questionOptionResponses = new ArrayList<>();
    questionOptions.forEach(
        questionOption -> {
          questionOptionResponses.add(
              new QuestionOptionResponse(
                  questionOption.getId().getId(),
                  questionOption.getId().getQuestionId(),
                  questionOption.getContent()));
        });
    return QuestionResponseNoAnswer.builder()
        .content(question.getContent())
        .options(questionOptionResponses)
        .quizId(question.getQuiz().getId())
        .build();
  }

  @Override
  public List<QuestionResponse> getAll() {
    List<Question> questions = questionRepository.findAll();
    List<QuestionResponse> questionResponses = new ArrayList<>();
    questions.forEach(
        question -> {
          List<QuestionOption> questionOptions =
              questionOptionRepository.findAllByQuestionId(question.getId());
          List<QuestionOptionResponse> questionOptionResponses = new ArrayList<>();
          questionOptions.forEach(
              questionOption -> {
                questionOptionResponses.add(
                    new QuestionOptionResponse(
                        questionOption.getId().getId(),
                        questionOption.getId().getQuestionId(),
                        questionOption.getContent()));
              });
          questionResponses.add(
              QuestionResponse.builder()
                  .content(question.getContent())
                  .options(questionOptionResponses)
                  .answer(question.getAnswer())
                  .quizId(question.getQuiz().getId())
                  .build());
        });
    return questionResponses;
  }

  @Override
  public void delete(Long questionId) {
    Question question = questionRepository.findById(questionId).orElse(null);
    if (question == null) {
      return;
    }
    List<QuestionOption> questionOptions = questionOptionRepository.findAllByQuestionId(questionId);
    questionOptionRepository.deleteAll(questionOptions);
    questionRepository.delete(question);
  }

  @Override
  public List<QuestionResponse> getQuestionByQuizId(Long quizId) {
    // Lấy tất cả câu hỏi của quizId
    List<Question> questions = questionRepository.findByQuizId(quizId);
    List<QuestionResponse> questionResponses = new ArrayList<>();

    // Chuyển đổi danh sách câu hỏi thành response
    questions.forEach(
        question -> {
          List<QuestionOption> questionOptions =
              questionOptionRepository.findAllByQuestionId(question.getId());
          List<QuestionOptionResponse> questionOptionResponses = new ArrayList<>();
          questionOptions.forEach(
              option -> {
                questionOptionResponses.add(
                    new QuestionOptionResponse(
                        option.getId().getId(),
                        option.getId().getQuestionId(),
                        option.getContent()));
              });
          questionResponses.add(
              QuestionResponse.builder()
                  .content(question.getContent())
                  .options(questionOptionResponses)
                  .answer(question.getAnswer())
                  .quizId(question.getQuiz().getId())
                  .build());
        });
    return questionResponses;
  }
}
