package com.example.nhom3_tt_.dtos.response.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
  private String content;

  private List<QuestionOptionResponse> options;

  private Long answer;

  private Long quizId;
}
