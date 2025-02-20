package com.example.nhom3_tt_.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailRequest {
  @NotNull(message = "Order's id cannot be null")
  @Positive(message = "Order's id must be a positive number")
  private Long orderId;

  @NotNull(message = "Course's id cannot be null")
  @Positive(message = "Course's id must be a positive number")
  private Long courseId;
}
