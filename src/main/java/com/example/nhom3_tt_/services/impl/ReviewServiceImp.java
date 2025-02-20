package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.ReviewRequest;
import com.example.nhom3_tt_.dtos.response.ReviewResponse;
import com.example.nhom3_tt_.exception.CustomException;
import com.example.nhom3_tt_.mappers.ReviewMapper;
import com.example.nhom3_tt_.models.Course;
import com.example.nhom3_tt_.models.Review;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.CourseRepository;
import com.example.nhom3_tt_.repositories.ReviewRepository;
import com.example.nhom3_tt_.services.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImp implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final CourseRepository courseRepository;
  private final ReviewMapper reviewMapper;

  @Override
  public List<ReviewResponse> findAllReviewsByCourseId(Long courseId, Pageable pageable) {
    List<Review> reviewList = reviewRepository.findAllReviewsByCourseId(courseId, pageable);

    return reviewList.stream()
        .map(reviewMapper::convertToReviewResponse)
        .toList();
  }

  @Override
  public ReviewResponse addReviewToCourse(Long courseId, ReviewRequest reviewRequest) {
    Course existingCourse = courseRepository.findById(courseId)
        .orElseThrow(() -> new CustomException("Course not found", 404));

    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Review existingReview = reviewRepository.findReviewByStudentIdAndCourseId(currentUser.getId(),
        courseId);

    if (existingReview != null) {
      throw new CustomException("You can only add 1 review in 1 course!", 409);
    }

    Review newReview = Review.builder()
        .course(existingCourse)
        .student(currentUser)
        .star(reviewRequest.getStar())
        .comment(reviewRequest.getComment())
        .build();

    reviewRepository.save(newReview);
    return reviewMapper.convertToReviewResponse(newReview);
  }

  @Override
  public ReviewResponse findReviewById(Long reviewId) {
    Review reviewFound = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException("Review not found", 404));

    return reviewMapper.convertToReviewResponse(reviewFound);
  }

  @Override
  public ReviewResponse updateReviewById(Long reviewId,
      ReviewRequest reviewRequest) {

    Review existingReview = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException("Review not found", 404));

    Long courseId = existingReview.getCourse().getId();

    Course existingCourse = courseRepository.findById(courseId)
        .orElseThrow(() -> new CustomException("Course not found", 404));

    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // Check the current is the author of review updated, if not throw exception
    validateReviewOwnership(existingReview, currentUser.getId());

    existingReview.setStar(reviewRequest.getStar());
    existingReview.setComment(reviewRequest.getComment());
    existingReview.setCourse(existingCourse);

    reviewRepository.save(existingReview);
    return reviewMapper.convertToReviewResponse(existingReview);
  }

  @Override
  public void deleteReviewById(Long reviewId) {
    Review existingReview = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException("Review not found", 404));

    Long courseId = existingReview.getCourse().getId();

    courseRepository.findById(courseId)
        .orElseThrow(() -> new CustomException("Course not found", 404));

    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    validateReviewOwnership(existingReview, currentUser.getId());

    reviewRepository.delete(existingReview);
  }

  public void validateReviewOwnership(Review review, Long userId) {
    if (!review.getStudent().getId().equals(userId)) {
      throw new CustomException(
          "You are not allowed to modify or delete a record that you do not own.", 403);
    }
  }

}
