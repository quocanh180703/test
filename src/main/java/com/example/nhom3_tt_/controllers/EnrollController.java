package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.EnrollRequest;
import com.example.nhom3_tt_.dtos.response.EnrollResponse;
import com.example.nhom3_tt_.dtos.response.PageResponse;
import com.example.nhom3_tt_.models.Enroll;
import com.example.nhom3_tt_.services.EnrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/enrolls")
public class EnrollController {
  private final EnrollService enrollService;

  @GetMapping("/")
  public ResponseEntity<?> getAllEnrolls(
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize) {
    Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
    var enrolls = enrollService.findAll(pageable);
    return ResponseEntity.ok().body(enrolls);
  }

  @GetMapping("/{enrollId}")
  public ResponseEntity<?> getEnrollById(@PathVariable Long enrollId) {
    return ResponseEntity.ok().body(enrollService.findById(enrollId));
  }

  @PostMapping("/")
  public ResponseEntity<?> enroll(@RequestBody @Valid EnrollRequest req) throws URISyntaxException {
    Enroll enroll = enrollService.enroll(req);
    return ResponseEntity.created(new URI("api/v1/enrolls/" + enroll.getId()))
        .body("Enroll successfully!");
  }

  @DeleteMapping("/{enrollId}")
  public ResponseEntity<?> deleteEnroll(@PathVariable Long enrollId) {
    enrollService.deleteEnrollById(enrollId);
    return ResponseEntity.ok().body("Delete successfully!");
  }

  @DeleteMapping("/student/{studentId}/course/{courseId}")
  public ResponseEntity<?> deleteEnroll(@PathVariable Long studentId, @PathVariable Long courseId) {
    enrollService.deleteEnroll(studentId, courseId);
    return ResponseEntity.ok().body("Delete successfully!");
  }

  @GetMapping("/student/{studentId}")
  public ResponseEntity<?> getEnrollsByStudentId(
      @PathVariable Long studentId,
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize) {
    Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
    var enrolls = enrollService.findAllByStudentId(studentId, pageable);
    return ResponseEntity.ok().body(enrolls);
  }

  @GetMapping("/course/{courseId}")
  public ResponseEntity<?> getEnrollsByCourseId(
      @PathVariable Long courseId,
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize) {
    Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
    var enrolls = enrollService.findAllByCourseId(courseId, pageable);
    return ResponseEntity.ok().body(enrolls);
  }
}
