package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.quiz.QuestionRequest;
import com.example.nhom3_tt_.dtos.requests.quiz.QuizRequest;
import com.example.nhom3_tt_.dtos.response.quiz.QuestionOptionResponse;
import com.example.nhom3_tt_.dtos.response.quiz.QuestionResponseNoAnswer;
import com.example.nhom3_tt_.dtos.response.quiz.QuizResponse;
import com.example.nhom3_tt_.dtos.response.quiz.QuizResponseWithQuestion;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.mappers.QuizMapper;
import com.example.nhom3_tt_.models.Question;
import com.example.nhom3_tt_.models.Quiz;
import com.example.nhom3_tt_.models.Section;
import com.example.nhom3_tt_.repositories.QuestionRepository;
import com.example.nhom3_tt_.repositories.QuizRepository;
import com.example.nhom3_tt_.repositories.SectionRepository;
import com.example.nhom3_tt_.services.QuestionService;
import com.example.nhom3_tt_.services.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {
  private final QuizRepository quizRepository;
  private final QuizMapper quizMapper;
  private final QuestionService questionService;
  private final SectionRepository sectionRepository;
  private final QuestionRepository questionRepository;

  @Override
  public Quiz getQuizEntityById(Long id) {
    Quiz quiz = quizRepository.findById(id).orElse(null);
    if (quiz == null) {
      throw new AppException(ErrorCode.QUIZ_NOT_FOUND);
    }
    return quiz;
  }

  @Override
  public QuizResponse getQuizById(Long id) {
    return quizMapper.toQuizResponse(quizRepository.findById(id).orElse(null));
  }

  @Override
  public QuizResponseWithQuestion getQuizQuestionById(Long id) {
    Quiz quiz = quizRepository.findById(id).orElse(null);
    if (quiz == null) {
      throw new AppException(ErrorCode.QUIZ_NOT_FOUND);
    }
    return QuizResponseWithQuestion.builder()
        .id(quiz.getId())
        .title(quiz.getTitle())
        .description(quiz.getDescription())
        .timeLimit(quiz.getTimeLimit())
        .startDate(quiz.getStartDate().toString())
        .endDate(quiz.getEndDate().toString())
        .attemptAllowed(quiz.getAttemptAllowed())
        .sectionId(quiz.getSection().getId())
        .questions(
            quiz.getQuestions().stream()
                .map(
                    question ->
                        QuestionResponseNoAnswer.builder()
                            .quizId(question.getQuiz().getId())
                            .content(question.getContent())
                            .options(
                                question.getOptions().stream()
                                    .map(
                                        option ->
                                            QuestionOptionResponse.builder()
                                                .id(option.getId().getId())
                                                .questionId(option.getQuestion().getId())
                                                .content(option.getContent())
                                                .build())
                                    .toList())
                            .build())
                .collect(Collectors.toList()))
        .build();
  }

  @Override
  public QuizResponse create(QuizRequest quizRequest) {
    if (quizRequest.getSectionId() == null
        || !sectionRepository.findById(quizRequest.getSectionId()).isPresent()) {
      throw new IllegalArgumentException("Section ID not found");
    }
    return quizMapper.toQuizResponse(quizRepository.save(quizMapper.toQuiz(quizRequest)));
  }

  @Override
  public QuizResponse update(Long id, QuizRequest quizRequest) {
    Quiz quiz =
        quizRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

    if (quizRequest.getTitle() != null) {
      quiz.setTitle(quizRequest.getTitle());
    }

    if (quizRequest.getDescription() != null) {
      quiz.setDescription(quizRequest.getDescription());
    }

    if (quizRequest.getTimeLimit() != null) {
      quiz.setTimeLimit(quizRequest.getTimeLimit());
    }

    if (quizRequest.getAttemptAllowed() != null) {
      quiz.setAttemptAllowed(quizRequest.getAttemptAllowed());
    }

    if (quizRequest.getStartDate() != null) {
      quiz.setStartDate(quizRequest.getStartDate());
    }
    if (quizRequest.getEndDate() != null) {
      quiz.setEndDate(quizRequest.getEndDate());
    }

    if (quizRequest.getSectionId() != null
        && !quizRequest.getSectionId().equals(quiz.getSection().getId())) {
      quiz.setSection(sectionRepository.findById(quizRequest.getSectionId()).get());
    }

    return quizMapper.toQuizResponse(quizRepository.save(quiz));
  }

  @Override
  public void delete(Long id) {
    List<Question> questionList = questionRepository.findByQuizId(id);
    questionRepository.deleteAll(questionList);
    quizRepository.deleteById(id);
  }

  @Override
  public List<QuizResponse> getAll() {
    return quizRepository.findAll().stream()
        .map(quizMapper::toQuizResponse)
        .collect(Collectors.toList());
  }

  @Override
  public void importQuizUsingExcel(MultipartFile questions, Long quizId) throws IOException {
    // Try block to check for exceptions

    log.info("File name: {}", questions.getOriginalFilename());

    // Create Workbook instance holding reference to
    // .xlsx file
    XSSFWorkbook workbook = new XSSFWorkbook(questions.getInputStream());

    // Get first/desired sheet from the workbook
    XSSFSheet sheet = workbook.getSheetAt(0);

    // Iterate through each rows one by one
    Iterator<Row> rowIterator = sheet.iterator();

    String content = "";
    List<String> options = null;
    int answer = 0;
    // Till there is an element condition holds true
    while (rowIterator.hasNext()) {
      options = new ArrayList<>();

      Row row = rowIterator.next();
      if (row.getRowNum() == 0) {
        continue;
      }

      log.info("currentRow: " + row.getRowNum());

      // For each row, iterate through all the
      // columns
      Iterator<Cell> cellIterator = row.cellIterator();

      while (cellIterator.hasNext()) {

        Cell cell = cellIterator.next();

        if (cell.getColumnIndex() == 0) {
          //            log.info(cell.toString());
          content = cell.getStringCellValue();
        } else if (cell.getColumnIndex() == 1) {
          //            log.info(cell.toString());
          answer = (int) cell.getNumericCellValue();
        } else {
          if (cell.getStringCellValue().isEmpty()) {
            break;
          }
          options.add(cell.getStringCellValue());
        }
      }
      QuestionRequest questionRequest =
          QuestionRequest.builder()
              .content(content)
              .options(options)
              .answer(answer)
              .quizId(quizId)
              .build();
      questionService.create(questionRequest);
    }
    workbook.close();
  }
}
