package com.example.nhom3_tt_.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewResponse {

  private Long id;
  private Long studentId;
  private String fullName;
  private Long courseId;
  private String courseTitle;
  private Double star;
  private String comment;
}
