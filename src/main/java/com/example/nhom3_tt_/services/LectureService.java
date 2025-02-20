package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.lecture.LectureRequest;
import com.example.nhom3_tt_.dtos.response.lecture.LectureResponse;
import com.example.nhom3_tt_.models.Lecture;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LectureService {
  LectureResponse create(Lecture lecture);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  LectureResponse createLecture(
      LectureRequest lecture, MultipartFile video, MultipartFile thumbnail);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  LectureResponse updateLecture(
      Long id, LectureRequest request, MultipartFile video, MultipartFile thumbnail);

  List<LectureResponse> getAllBySectionId(Long sectionId);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  LectureResponse update(Lecture lecture);

  List<LectureResponse> getAll();

  LectureResponse getById(Long id);

  Lecture getEntityById(Long id);

  void delete(Long id);
}
