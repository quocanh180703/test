package com.example.nhom3_tt_.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequest {

  @NotNull(message = "Course ID cannot be null")
  private Long courseId;

  @NotNull(message = "Cart ID cannot be null")
  private Long cartId;
}
