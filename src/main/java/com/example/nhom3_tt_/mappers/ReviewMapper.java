package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.response.ReviewResponse;
import com.example.nhom3_tt_.models.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

  Review convertToReview(ReviewResponse reviewResponse);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "student.id", target = "studentId")
  @Mapping(source = "student.fullname", target = "fullName")
  @Mapping(source = "course.id", target = "courseId")
  @Mapping(source = "course.title", target = "courseTitle")
  @Mapping(source = "star", target = "star")
  @Mapping(source = "comment", target = "comment")
  ReviewResponse convertToReviewResponse(Review review);
}
