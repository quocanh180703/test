package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.quiz.QuizRequest;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.services.QuizService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/quizzes")
@RequiredArgsConstructor
public class QuizController {
  private final QuizService quizService;

  @GetMapping("")
  public ResponseEntity<?> getAllQuiz() {
    return ResponseEntity.ok().body(quizService.getAll());
  }

  @GetMapping("/{quizId}")
  public ResponseEntity<?> getQuizById(@PathVariable Long quizId) {
    return ResponseEntity.ok().body(quizService.getQuizById(quizId));
  }

  @GetMapping("/{quizId}/questions")
  public ResponseEntity<?> getQuestionsByQuizId(@PathVariable Long quizId) {
    return ResponseEntity.ok().body(quizService.getQuizQuestionById(quizId));
  }

  @PostMapping("")
  public ResponseEntity<?> createQuiz(@RequestBody @Valid QuizRequest quizRequest) {
    return ResponseEntity.ok().body(quizService.create(quizRequest));
  }

  @PatchMapping("/{quizId}")
  public ResponseEntity<?> editQuiz(
      @PathVariable Long quizId, @RequestBody QuizRequest quizRequest) {
    return ResponseEntity.ok().body(quizService.update(quizId, quizRequest));
  }

  @DeleteMapping("/{quizId}")
  public ResponseEntity<?> deleteQuiz(@PathVariable Long quizId) {
    quizService.delete(quizId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/{quizId}/questions", consumes = "multipart/form-data")
  public ResponseEntity<?> importExcelQuestion(
      @PathVariable String quizId, @RequestPart("file") MultipartFile questionFile)
      throws IOException {

    // Kiểm tra file null hoặc rỗng
    if (questionFile == null || questionFile.isEmpty()) {
      throw new AppException(ErrorCode.FILE_NOT_EMPTY);
    }

    quizService.importQuizUsingExcel(questionFile, Long.parseLong(quizId));
    return ResponseEntity.ok().build();
  }
}
