package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.CourseRequest;
import com.example.nhom3_tt_.dtos.response.CourseResponse;
import com.example.nhom3_tt_.models.Category;
import com.example.nhom3_tt_.models.Course;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

public interface CourseService {

  @PreAuthorize("hasAuthority('INSTRUCTOR') || hasAuthority('ADMIN')")
  CourseResponse create(CourseRequest courseRequest);

  @PreAuthorize("hasAuthority('ADMIN')")
  List<CourseResponse> getAll(Pageable pageable);

  // public
  List<CourseResponse> getAllApproved(Pageable pageable);

  @PreAuthorize("hasAuthority('ADMIN')")
  List<CourseResponse> getAllPending(Pageable pageable);

  @PreAuthorize("hasAuthority('ADMIN')")
  List<CourseResponse> getAllReject(Pageable pageable);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  List<CourseResponse> getAll();

  CourseResponse getById(Long id);

  Course getByIdEntity(Long id);

  List<Course> getCoursesByCategory(Category category);

  List<CourseResponse> getCoursesByLevel(String level, Pageable pageable);

  List<CourseResponse> getCoursesByCategoryId(Long categoryId, Pageable pageable);

  List<CourseResponse> getCourseByIntructorId(Long instructorId, Pageable pageable);

  List<CourseResponse> searchByTitle(String title, Pageable pageable);

  @PreAuthorize("hasAuthority('INSTRUCTOR')|| hasAuthority('ADMIN')")
  CourseResponse update(Long id, CourseRequest newCourse);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  CourseResponse forceDelete(Long id);

  @PreAuthorize("hasAuthority('ADMIN')")
  CourseResponse approveCourse(Long id);

  @PreAuthorize("hasAuthority('ADMIN')")
  CourseResponse rejectCourse(Long id);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  CourseResponse uploadThumbnail(Long id, MultipartFile thumbnail) throws IOException;

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  CourseResponse uploadVideo(Long id, MultipartFile video) throws IOException;
}
