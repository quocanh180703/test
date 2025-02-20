package com.example.nhom3_tt_.dtos.requests.quizSubmission;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSubmissionAnswerRequest {
  private Long quizSubmissionId;
  private Long questionId;
  private Long answer;
}
