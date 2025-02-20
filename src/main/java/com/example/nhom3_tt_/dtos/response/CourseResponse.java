package com.example.nhom3_tt_.dtos.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {

  private Long id;
  private String instructorName;
  private String categoryName;
  private String title;
  private String shortDescription;
  private String level;
  private double regularPrice;
  private Date publishDay;
  private String status;
  private String language;
  private String thumbnail;
  private boolean requireLogin;
  private String introVideo;
  private String requirement;
  private String objective;
  private String description;
  private String closeCaption;
}
