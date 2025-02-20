package com.example.nhom3_tt_.dtos.requests.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionRequest {

  @NotBlank(message = "Question content is required")
  private String content;

  @NotNull(message = "Question options is required")
  @Size(min = 1, message = "There must be at least one option")
  private List<String> options;

  @NotNull(message = "Question answer is required")
  private Integer answer;

  @NotNull(message = "Quiz id is required")
  private Long quizId;

  @JsonIgnore
  @AssertTrue(message = "There must be at least two valid options")
  public boolean isOptionsValid() {
    return options != null
        && !options.isEmpty()
        && options.stream().noneMatch(String::isBlank)
        && options.size() >= 2;
  }

  @JsonIgnore
  @AssertTrue(message = "Answer index must be less than or equal the number of options")
  public boolean isAnswerValid() {
    return answer != null && options != null && answer <= options.size();
  }
}
