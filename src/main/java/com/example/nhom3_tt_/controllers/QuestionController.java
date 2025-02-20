package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.quiz.QuestionRequest;
import com.example.nhom3_tt_.dtos.response.quiz.QuestionResponse;
import com.example.nhom3_tt_.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
  private final QuestionService questionService;

  @GetMapping("")
  public ResponseEntity<?> getAllQuestion() {
    return ResponseEntity.ok().body(questionService.getAll());
  }

  @GetMapping("/{questionId}")
  public ResponseEntity<?> getQuestionById(@PathVariable Long questionId) {
    return ResponseEntity.ok().body(questionService.getQuestionByIdNoAnswer(questionId));
  }

  @GetMapping("/quiz/{quizId}")
  public ResponseEntity<?> getQuestionByQuizId(@PathVariable Long quizId) {
    List<QuestionResponse> questions = questionService.getQuestionByQuizId(quizId);
    if (questions.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok().body(questions);
  }

  @PostMapping("")
  public ResponseEntity<?> createQuestion(@RequestBody @Valid QuestionRequest questionRequest) {
    return ResponseEntity.ok().body(questionService.create(questionRequest));
  }

  @PutMapping("/{questionId}")
  public ResponseEntity<?> updateQuestion(
      @PathVariable Long questionId, @RequestBody @Valid QuestionRequest questionRequest) {
    return ResponseEntity.ok().body(questionService.update(questionId, questionRequest));
  }

  @PatchMapping("/{questionId}")
  public ResponseEntity<?> patchQuestion(
      @PathVariable Long questionId, @RequestBody QuestionRequest questionRequest) {
    return ResponseEntity.ok().body(questionService.update(questionId, questionRequest));
  }

  @DeleteMapping("/{questionId}")
  public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
    questionService.delete(questionId);
    return ResponseEntity.noContent().build();
  }
}
