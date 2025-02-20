package com.example.nhom3_tt_.dtos.response.quiz;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseNoAnswer {
  private String content;

  private List<QuestionOptionResponse> options;

  private Long quizId;
}
