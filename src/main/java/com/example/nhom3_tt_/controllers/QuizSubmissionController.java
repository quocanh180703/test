package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.quizSubmission.QuizSubmissionRequest;
import com.example.nhom3_tt_.services.QuizSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz-submissions")
@RequiredArgsConstructor
public class QuizSubmissionController {
  private final QuizSubmissionService quizSubmissionService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getQuizSubmissionById(@PathVariable Long id) {
    return ResponseEntity.ok(quizSubmissionService.getById(id));
  }

  @PostMapping("")
  public ResponseEntity<?> createQuizSubmission(@RequestBody QuizSubmissionRequest request) {
    return ResponseEntity.ok(quizSubmissionService.create(request));
  }

  @GetMapping("/student/{studentId}")
  public ResponseEntity<?> getQuizSubmissionByStudentId(@PathVariable Long studentId) {
    return ResponseEntity.ok(quizSubmissionService.getByStudentId(studentId));
  }

  @GetMapping("/student/logged-in")
  public ResponseEntity<?> getQuizSubmissionByLoggedInStudent() {
    return ResponseEntity.ok(quizSubmissionService.getByLoggedInUser());
  }

  @GetMapping("/quiz/{quizId}")
  public ResponseEntity<?> getQuizSubmissionByQuizId(@PathVariable Long quizId) {
    return ResponseEntity.ok(quizSubmissionService.getByQuizId(quizId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteQuizSubmissionById(@PathVariable Long id) {
    quizSubmissionService.deleteById(id);
    return ResponseEntity.ok("Deleted");
  }
}
