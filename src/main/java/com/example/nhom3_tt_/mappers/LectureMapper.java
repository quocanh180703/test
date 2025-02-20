package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.lecture.LectureRequest;
import com.example.nhom3_tt_.dtos.response.lecture.LectureResponse;
import com.example.nhom3_tt_.models.Lecture;
import com.example.nhom3_tt_.services.SectionService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = {SectionService.class})
public interface LectureMapper {

  @Mapping(source = "sectionId", target = "section")
  Lecture toLecture(LectureRequest lectureRequest);

  //  @Mapping(source = "section.name", target = "sectionName")
  LectureResponse toLectureResponse(Lecture lecture);

  @Mapping(
      source = "sectionId",
      target = "section",
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(
      target = "title",
      source = "title",
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(
      target = "description",
      source = "description",
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(
      target = "preview",
      source = "preview",
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Lecture updateLecture(LectureRequest request, @MappingTarget Lecture lecture);
}
