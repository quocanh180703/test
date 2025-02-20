package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.ReviewRequest;
import com.example.nhom3_tt_.dtos.response.ReviewResponse;
import com.example.nhom3_tt_.services.ReviewService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

  private final ReviewService reviewService;

  @GetMapping("/courses/{courseId}/reviews")
  public ResponseEntity<List<ReviewResponse>> getAllReviewsForCourse(
      @PathVariable Long courseId, @ParameterObject Pageable pageable) {
    return ResponseEntity.ok(reviewService.findAllReviewsByCourseId(courseId, pageable));
  }

  @PostMapping("/courses/{courseId}/reviews")
  public ResponseEntity<ReviewResponse> addReviewToCourse(
      @PathVariable("courseId") Long courseId, @Valid @RequestBody ReviewRequest reviewRequest) {
    return ResponseEntity.ok(reviewService.addReviewToCourse(courseId, reviewRequest));
  }

  @GetMapping("/reviews/{reviewId}")
  public ResponseEntity<ReviewResponse> getReviewById(@PathVariable("reviewId") Long reviewId) {
    return ResponseEntity.ok(reviewService.findReviewById(reviewId));
  }

  @PutMapping("/courses/reviews/{reviewId}")
  public ResponseEntity<ReviewResponse> updateReviewById(
      @PathVariable("reviewId") Long reviewId, ReviewRequest reviewRequest) {
    return ResponseEntity.ok(reviewService.updateReviewById(reviewId, reviewRequest));
  }

  @DeleteMapping("/courses/reviews/{reviewId}")
  public ResponseEntity<?> deleteReviewById(@PathVariable("reviewId") Long reviewId) {
    reviewService.deleteReviewById(reviewId);
    return ResponseEntity.noContent().build();
  }
}
