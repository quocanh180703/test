package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.ReviewRequest;
import com.example.nhom3_tt_.dtos.response.ReviewResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

  ReviewResponse addReviewToCourse(Long courseId, ReviewRequest reviewRequest);

  ReviewResponse findReviewById(Long reviewId);

  ReviewResponse updateReviewById(Long reviewId,
      ReviewRequest reviewRequest);

  void deleteReviewById(Long reviewId);

  List<ReviewResponse> findAllReviewsByCourseId(Long courseId, Pageable pageable);
}
