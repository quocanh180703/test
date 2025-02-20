package com.example.nhom3_tt_.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollResponse {
  private Long id;
  private Long studentId;
  private Long courseId;
}
