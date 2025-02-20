package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.lecture.LectureRequest;
import com.example.nhom3_tt_.dtos.requests.lecture.LectureVideoFolderStructure;
import com.example.nhom3_tt_.dtos.response.lecture.LectureResponse;
import com.example.nhom3_tt_.mappers.LectureMapper;
import com.example.nhom3_tt_.models.Course;
import com.example.nhom3_tt_.models.Section;
import com.example.nhom3_tt_.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/lecture")
@RequiredArgsConstructor
public class LectureController {

  private final CloudinaryService cloudinaryService;
  private final LectureService lectureService;
  private final LectureMapper lectureMapper;
  private final SectionService sectionService;
  private final CourseService courseService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> getLecture(@PathVariable Long id) {
    return ResponseEntity.ok(lectureService.getById(id));
  }

  @GetMapping(value = "")
  public ResponseEntity<?> getAllLectures() {
    return ResponseEntity.ok(lectureService.getAll());
  }

  @PostMapping(value = "", consumes = "multipart/form-data")
  public ResponseEntity<?> createLecture(
      @RequestPart(value = "video", required = false) MultipartFile video,
      @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
      @RequestPart("lecture") @Valid LectureRequest request) {

    LectureResponse response = lectureService.createLecture(request, video, thumbnail);

    return ResponseEntity.ok(response);
  }

  @PutMapping(value = "/{id}", consumes = "multipart/form-data")
  public ResponseEntity<?> updateLecture(
      @PathVariable Long id,
      @RequestPart("video") @Nullable MultipartFile video,
      @RequestPart("thumbnail") @Nullable MultipartFile thumbnail,
      @RequestPart("lecture") @Nullable @Valid LectureRequest request) {
    LectureResponse response = lectureService.updateLecture(id, request, video, thumbnail);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteLecture(@PathVariable Long id) {
    lectureService.delete(id);
    return ResponseEntity.ok("Delete lecture successfully!");
  }

  private LectureVideoFolderStructure createFolderStructure(Long sectionId) {
    Section section = sectionService.getEntityById(sectionId);
    Course courseId = courseService.getByIdEntity(section.getCourse().getId());
    Long instructorId = courseId.getInstructor().getId();
    return LectureVideoFolderStructure.builder()
        .instructorId(instructorId)
        .courseId(courseId.getId())
        .sectionId(section.getId())
        .build();
  }
}
