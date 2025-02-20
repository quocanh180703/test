package com.example.nhom3_tt_.dtos.requests;

import com.example.nhom3_tt_.validator.CustomDateDeserializer;
import com.example.nhom3_tt_.validator.NoSpecialCharacters;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponRequest {
  @NotBlank(message = "Code must not be empty")
  @NoSpecialCharacters
  private String code;

  @NotNull(message = "Begin day must not be null")
  @FutureOrPresent(message = "Begin day must be today or in the future")
  private LocalDateTime beginDay;

  @NotNull(message = "Expire day must not be null")
  @Future(message = "Expire day must be in the future")
  private LocalDateTime expireDay;

  @DecimalMin(
      value = "0.0",
      inclusive = false,
      message = "Discount percentage must be greater than 0")
  @DecimalMax(value = "100.0", message = "Discount percentage must not exceed 100")
  private double percentDiscount;
}
