package com.example.nhom3_tt_.dtos.response.quizSubmission;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSubmissionResponse {
  private Long id;
  private Long quizId;
  private String quizTitle;
  private Double score;
  private int totalTimes;
  private int totalCorrects;
  private int totalQuestions;
  private List<CustomAnswerResponse> answers;
}
