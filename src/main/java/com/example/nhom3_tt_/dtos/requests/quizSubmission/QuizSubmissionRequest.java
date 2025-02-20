package com.example.nhom3_tt_.dtos.requests.quizSubmission;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSubmissionRequest {
  private int totalTimes;
  private Long quizId;
  private List<QuizSubmissionAnswerRequest> answers;
}
