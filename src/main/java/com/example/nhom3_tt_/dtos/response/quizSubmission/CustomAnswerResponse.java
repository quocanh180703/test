package com.example.nhom3_tt_.dtos.response.quizSubmission;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomAnswerResponse {
  private Long id;
  private Long quizSubmissionId;
  private Long questionId;
  private String questionContent;
  private String answerContent;
  private String correctAnswerContent;
  private boolean isCorrect;
}
