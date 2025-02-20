package com.example.nhom3_tt_.dtos.requests.subscription;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDeleteRequest {

  @NotBlank(message = "Instructor id must not blank")
  private Long student_id;

  @NotBlank(message = "Instructor id must not blank")
  private Long instructor_id;
}
