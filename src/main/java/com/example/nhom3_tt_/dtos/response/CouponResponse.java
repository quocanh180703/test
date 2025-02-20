package com.example.nhom3_tt_.dtos.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponse {
  private Long id;
  private String code;
  private LocalDateTime beginDay;
  private LocalDateTime expireDay;
  private double percentDiscount;
}
