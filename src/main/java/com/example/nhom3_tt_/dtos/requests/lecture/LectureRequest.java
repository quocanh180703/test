package com.example.nhom3_tt_.dtos.requests.lecture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureRequest {
  private Long sectionId;
  private String title;
  private String description;
  private Boolean preview;
}
