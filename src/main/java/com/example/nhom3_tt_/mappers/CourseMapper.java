package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.CourseRequest;
import com.example.nhom3_tt_.dtos.response.CourseResponse;
import com.example.nhom3_tt_.exception.CustomException;
import com.example.nhom3_tt_.models.*;
import org.mapstruct.*;
import org.springframework.http.HttpStatus;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMapper {

  @Mapping(source = "instructorId", target = "instructor.id")
  @Mapping(source = "categoryId", target = "category.id")
  @Mapping(target = "thumbnail", expression = "java(mapThumbnail(courseRequest.getThumbnail()))")
  @Mapping(target = "status", expression = "java(mapStatus(courseRequest.getStatus()))")
  @Mapping(target = "level", expression = "java(mapLevel(courseRequest.getLevel()))")
  Course convertToEntity(CourseRequest courseRequest);

  @Mapping(source = "instructor.fullname", target = "instructorName")
  @Mapping(source = "category.name", target = "categoryName")
  @Mapping(target = "status", expression = "java(mapStatusToString(course.getStatus()))")
  @Mapping(target = "level", expression = "java(mapLevelToString(course.getLevel()))")
  CourseResponse convertToResponse(Course course);

  void updateCourseFromRequest(CourseRequest courseRequest, @MappingTarget Course course);

  // Helper method to handle default value for thumbnail
  default String mapThumbnail(String thumbnail) {
    return (thumbnail == null || thumbnail.isEmpty())
        ? "https://res.cloudinary.com/dftznqjsj/image/upload/v1732778656/default-placeholder-300x300_g2ygvg.png"
        : thumbnail;
  }

  // Helper methods to map between String and ECourseStatus
  default ECourseStatus mapStatus(String status) {
    if (status == null) {
      return null;
    }
    try {
      return ECourseStatus.valueOf(status.toUpperCase()); // Convert String to Enum
    } catch (IllegalArgumentException e) {
      throw new CustomException("Invalid status value: " + status, HttpStatus.BAD_REQUEST.value());
    }
  }

  default String mapStatusToString(ECourseStatus status) {
    return status != null ? status.name() : null; // Convert Enum to String
  }

  // Helper methods to map between String and ETypeLevel
  default ETypeLevel mapLevel(String level) {
    if (level == null) {
      return null;
    }
    try {
      return ETypeLevel.valueOf(level.toUpperCase()); // Convert String to Enum (ignores case)
    } catch (IllegalArgumentException e) {
      throw new CustomException("Invalid level value: " + level, HttpStatus.BAD_REQUEST.value());
    }
  }

  // Helper method to convert ETypeLevel to String (for response mapping)
  default String mapLevelToString(ETypeLevel level) {
    return level != null ? level.name() : null; // Convert Enum to String
  }
}
