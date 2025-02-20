package com.example.nhom3_tt_.dtos.response.quizSubmission;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSubmissionAnswerResponse {
  private Long id;
  private Long quizSubmissionId;
  private Long questionId;
  private String questionContent;
  private Long answer;
}
