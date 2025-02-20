package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.Review;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findAllReviewsByCourseId(Long courseId, Pageable pageable);

  Review findReviewByStudentIdAndCourseId(Long studentId, Long courseId);
}
