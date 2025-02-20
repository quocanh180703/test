package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.domain.RestResponse;
import com.example.nhom3_tt_.dtos.requests.CourseRequest;
import com.example.nhom3_tt_.dtos.response.CourseResponse;
import com.example.nhom3_tt_.exception.NotFoundException;
import com.example.nhom3_tt_.services.CourseService;
import com.example.nhom3_tt_.services.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;
  private final SectionService sectionService;

  @PostMapping("")
  public ResponseEntity<?> create(@Valid @RequestBody CourseRequest courseRequest) {
    return ResponseEntity.ok(courseService.create(courseRequest));
  }

  @GetMapping("")
  public ResponseEntity<?> getAll(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(courseService.getAll(pageable));
  }

  @GetMapping("/get-approveds")
  public ResponseEntity<?> getAllApproved(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(courseService.getAllApproved(pageable));
  }

  @GetMapping("/get-rejects")
  public ResponseEntity<?> getAllReject(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(courseService.getAllReject(pageable));
  }

  @GetMapping("/get-pendings")
  public ResponseEntity<?> getAllPending(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(courseService.getAllPending(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(courseService.getById(id));
  }

  @GetMapping("/{id}/sections")
  public ResponseEntity<?> getSectionsByCourseId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(sectionService.getAllByCourseId(id));
  }

  // get courses by level
  @GetMapping("/level/{level}")
  public ResponseEntity<?> getByLevel(
      @PathVariable("level") String level, @ParameterObject Pageable pageable) {
    return ResponseEntity.ok(courseService.getCoursesByLevel(level, pageable));
  }

  @GetMapping("/category/{id}")
  public ResponseEntity<?> getByCategoryId(
      @PathVariable("id") Long id, @ParameterObject Pageable pageable) {
    return ResponseEntity.ok(courseService.getCoursesByCategoryId(id, pageable));
  }

  @GetMapping("/instructor/{id}")
  public ResponseEntity<?> getByInstructorId(
      @PathVariable("id") Long id, @ParameterObject Pageable pageable) {
    return ResponseEntity.ok(courseService.getCourseByIntructorId(id, pageable));
  }

  @GetMapping("/title")
  public ResponseEntity<?> searchByTitle(
      @RequestParam("title") String title, @ParameterObject Pageable pageable) {
    return ResponseEntity.ok(courseService.searchByTitle(title, pageable));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable("id") Long id, @Valid @RequestBody CourseRequest newCourse) {
    return ResponseEntity.ok(courseService.update(id, newCourse));
  }

  @PostMapping("/{id}/upload-thumbnail")
  public ResponseEntity<?> uploadThumbnail(
      @PathVariable("id") Long id, @RequestPart("file") MultipartFile file) throws IOException {
    return ResponseEntity.ok(courseService.uploadThumbnail(id, file));
  }

  @PostMapping("/{id}/upload-intro-video")
  public ResponseEntity<?> uploadIntroVideo(
      @PathVariable("id") Long id, @RequestPart("file") MultipartFile file) throws IOException {
    return ResponseEntity.ok(courseService.uploadVideo(id, file));
  }

  @DeleteMapping("/force-delete/{id}")
  public ResponseEntity<?> forceDelete(@PathVariable("id") Long id) {
    return ResponseEntity.ok(courseService.forceDelete(id));
  }

  @PutMapping("/{courseID}/approve")
  public ResponseEntity<?> approve(@PathVariable("courseID") Long courseId) {
    try {
      CourseResponse courseResponse = courseService.approveCourse(courseId);

      return ResponseEntity.ok().body(courseResponse);

    } catch (IllegalStateException e) {
      return ResponseEntity.status(400).build();

    } catch (NotFoundException e) {
      return ResponseEntity.status(404).build();
    }
  }

  @PutMapping("/{courseID}/reject")
  public ResponseEntity<?> reject(@PathVariable("courseID") Long courseId) {
    try {
      CourseResponse courseResponse = courseService.rejectCourse(courseId);

      return ResponseEntity.ok().body(courseResponse);

    } catch (IllegalStateException e) {
      return ResponseEntity.status(400).build();

    } catch (NotFoundException e) {
      return ResponseEntity.status(404).build();
    }
  }
}
