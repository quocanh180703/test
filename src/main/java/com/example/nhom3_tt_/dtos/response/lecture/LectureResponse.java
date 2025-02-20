package com.example.nhom3_tt_.dtos.response.lecture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureResponse {
  private Long id;
  //  private Long sectionName;
  private String title;
  private String description;
  private boolean preview;
  private String linkVideo;
  private String thumbnail;
}
