package com.example.nhom3_tt_.dtos.response.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class QuestionOptionResponse {
  private Long id;
  private Long questionId;
  private String content;
}
