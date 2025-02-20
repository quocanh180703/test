package com.example.nhom3_tt_.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {
  @NotNull(message = "Student ID cannot be null")
  private Long studentId;
}
