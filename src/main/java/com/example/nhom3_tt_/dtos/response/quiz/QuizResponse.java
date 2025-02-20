package com.example.nhom3_tt_.dtos.response.quiz;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizResponse {
  private Long id;
  private String title;
  private String description;
  private int timeLimit;
  private String startDate;
  private String endDate;
  private int attemptAllowed;
  private Long sectionId;
}
