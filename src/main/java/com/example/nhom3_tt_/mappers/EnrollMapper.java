package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.EnrollRequest;
import com.example.nhom3_tt_.dtos.response.EnrollResponse;
import com.example.nhom3_tt_.models.Enroll;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EnrollMapper {

  Enroll convertToEntity(EnrollRequest enrollRequest);

  @Mapping(source = "student.id", target = "studentId")
  @Mapping(source = "course.id", target = "courseId")
  EnrollResponse convertToResponse(Enroll enroll);
}
