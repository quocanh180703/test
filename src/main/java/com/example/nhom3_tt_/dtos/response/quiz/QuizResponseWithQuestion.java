package com.example.nhom3_tt_.dtos.response.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class QuizResponseWithQuestion {
  private Long id;
  private String title;
  private String description;
  private int timeLimit;
  private String startDate;
  private String endDate;
  private int attemptAllowed;
  private Long sectionId;
  private List<QuestionResponseNoAnswer> questions;
}
