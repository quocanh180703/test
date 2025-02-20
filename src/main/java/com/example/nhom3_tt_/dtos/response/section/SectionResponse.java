package com.example.nhom3_tt_.dtos.response.section;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectionResponse {
  private Long id;
  private String name;
  private String description;
  private String courseTitle;
  private int position;
}
