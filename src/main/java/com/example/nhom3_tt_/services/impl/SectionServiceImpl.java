package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.response.section.SectionResponse;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.exception.NotFoundException;
import com.example.nhom3_tt_.mappers.SectionMapper;
import com.example.nhom3_tt_.models.Section;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.SectionRepository;
import com.example.nhom3_tt_.services.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SectionServiceImpl implements SectionService {

  private final SectionRepository sectionRepository;
  private final SectionMapper mapper;

  @Override
  public List<SectionResponse> getAll() {
    return sectionRepository.findAll().stream().map(mapper::toSectionResponse).toList();
  }

  @Override
  public SectionResponse getById(Long id) {
    return mapper.toSectionResponse(
        sectionRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Section not found")));
  }

  @Override
  public Section getEntityById(Long id) {
    return sectionRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Section not found"));
  }

  @Override
  public List<SectionResponse> getAllByCourseId(Long courseId) {
    return sectionRepository.findAllByCourseId(courseId).stream()
        .map(mapper::toSectionResponse)
        .toList();
  }

  @Override
  public SectionResponse create(Section section) {
    if (!Objects.equals(
        section.getCourse().getInstructor().getId(),
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
      throw new AppException(ErrorCode.NOT_INSTRUCTOR);
    }
    return mapper.toSectionResponse(sectionRepository.save(section));
  }

  @Override
  public SectionResponse update(Long id, Section section) {

    if (!Objects.equals(
        section.getCourse().getInstructor().getId(),
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
      throw new AppException(ErrorCode.NOT_INSTRUCTOR);
    }

    Section existingSection =
        sectionRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Section not found"));
    existingSection.setName(section.getName());
    existingSection.setDescription(section.getDescription());
    existingSection.setCourse(section.getCourse());
    existingSection.setPosition(section.getPosition());
    return mapper.toSectionResponse(sectionRepository.save(existingSection));
  }

  @Override
  public void delete(Long id) {
    if (!Objects.equals(
        sectionRepository.findById(id).get().getCourse().getInstructor().getId(),
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
      throw new AppException(ErrorCode.NOT_INSTRUCTOR);
    }

    sectionRepository.deleteById(id);
  }
}
