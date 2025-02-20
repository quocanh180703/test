package com.example.nhom3_tt_.dtos.requests;

import com.example.nhom3_tt_.models.*;
import com.example.nhom3_tt_.validator.CustomDateDeserializer;
import com.example.nhom3_tt_.validator.NoSpecialCharacters;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRequest {

  @NotNull(message = "Instructor's id cannot be null")
  @Positive(message = "Instructor's id id must be a positive number")
  private Long instructorId;

  @NotNull(message = "Category's cannot be null")
  @Positive(message = "Category's id id must be a positive number")
  private Long categoryId;

  @NotBlank(message = "Title cannot be blank")
  @Size(max = 255, message = "Title must not exceed 255 characters")
  private String title;

  @Size(max = 255, message = "Short description must not exceed 255 characters")
  private String shortDescription;

  @NotBlank(message = "Level cannot be blank")
  @NoSpecialCharacters
  private String level;

  @NotNull(message = "Regular price cannot be null")
  @Positive(message = "Regular price must be greater than 0")
  @DecimalMin(value = "20000.0", message = "Regular price must be at least 20000")
  @DecimalMax(
      value = "10000000.0",
      message = "Regular price must be less than or equal to 10,000,000")
  private double regularPrice;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
  @NotNull(message = "Publish day cannot be null")
  private Date publishDay;

  @NoSpecialCharacters private String status;

  @NotBlank(message = "Language cannot be blank")
  @Pattern(regexp = "English|Vietnamese", message = "Language must be either English or Vietnamese")
  @NoSpecialCharacters
  private String language;

  private String thumbnail;

  private boolean requireLogin;

  private String introVideo;

  @Size(max = 500, message = "Requirement must not exceed 500 characters")
  private String requirement;

  @Size(max = 500, message = "Objective must not exceed 500 characters")
  private String objective;

  @Size(max = 500, message = "Objective must not exceed 500 characters")
  private String description;

  @Size(max = 500, message = "Objective must not exceed 500 characters")
  private String closeCaption;
}
