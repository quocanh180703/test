package com.example.nhom3_tt_.dtos.requests.quiz;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizRequest {
  private Long sectionId;

  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "Description is required")
  private String description;

  @Min(value = 1, message = "Time limit must be greater than 0")
  private Integer timeLimit;

  @Min(value = 1, message = "Attempt allowed must be greater than 0")
  private Integer attemptAllowed;

  @Future(message = "Start date must be in the future")
  private LocalDateTime startDate;

  @Future(message = "End date must be in the future")
  private LocalDateTime endDate;

  @AssertTrue(message = "End date must be after start date")
  private boolean isEndDateAfterStartDate() {
    return endDate == null || startDate == null || endDate.isAfter(startDate);
  }
}
