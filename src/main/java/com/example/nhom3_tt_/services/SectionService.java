package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.response.section.SectionResponse;
import com.example.nhom3_tt_.models.Section;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SectionService {

  List<SectionResponse> getAll();

  SectionResponse getById(Long id);

  Section getEntityById(Long id);

  List<SectionResponse> getAllByCourseId(Long courseId);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  SectionResponse create(Section section);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  SectionResponse update(Long id, Section section);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  void delete(Long id);
}
