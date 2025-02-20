package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.EnrollRequest;
import com.example.nhom3_tt_.dtos.response.EnrollResponse;
import com.example.nhom3_tt_.dtos.response.PageResponse;
import com.example.nhom3_tt_.models.Enroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnrollService {

  Enroll enroll(EnrollRequest req);

  EnrollResponse findById(Long id);

  PageResponse<?> findAll(Pageable pageable);

  PageResponse<?> findAllByStudentId(Long studentId, Pageable pageable);

  PageResponse<?> findAllByCourseId(Long courseId, Pageable pageable);

  void deleteEnrollById(Long id);

  void deleteEnroll(Long studentId, Long courseId);

  Boolean isEnrolled(Long studentId, Long courseId);
}
