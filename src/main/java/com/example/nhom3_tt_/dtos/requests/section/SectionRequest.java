package com.example.nhom3_tt_.dtos.requests.section;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionRequest {
  @NotNull(message = "Course ID cannot be null")
  private Long courseId;

  @NotBlank(message = "Name cannot be null")
  private String name;

  @NotBlank(message = "Description cannot be null")
  private String description;

  @Min(value = 0, message = "Position must be greater than or equal to 0")
  private int position;
}
