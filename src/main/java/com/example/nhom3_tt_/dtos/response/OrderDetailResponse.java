package com.example.nhom3_tt_.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
  private Long id;
  private Long orderId;
  private CourseResponse course;
  private Double price;
}
