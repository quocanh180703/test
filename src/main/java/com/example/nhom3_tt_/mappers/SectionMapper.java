package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.section.SectionRequest;
import com.example.nhom3_tt_.dtos.response.section.SectionResponse;
import com.example.nhom3_tt_.models.Section;
import com.example.nhom3_tt_.services.CourseService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {CourseService.class})
public interface SectionMapper {

  @Mapping(source = "courseId", target = "course")
  Section toSection(SectionRequest sectionRequest);

  @Mapping(source = "course.title", target = "courseTitle")
  SectionResponse toSectionResponse(Section section);
}
