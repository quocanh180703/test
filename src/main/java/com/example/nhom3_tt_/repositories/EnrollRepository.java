package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.CartItem;
import com.example.nhom3_tt_.models.Course;
import com.example.nhom3_tt_.models.Enroll;
import java.util.Optional;

import com.example.nhom3_tt_.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {
  Optional<Enroll> findByStudentIdAndCourseId(Long studentId, Long courseId);

  Page<Enroll> findAll(Pageable pageable);

  Page<Enroll> findAllByStudentId(Long studentId, Pageable pageable);

  Page<Enroll> findAllByCourseId(Long courseId, Pageable pageable);

  @Query("SELECT e FROM Enroll e WHERE e.student = :student AND e.course = :course")
  Enroll getEnrollByStudentAndCourseId(@Param("student") User student, @Param("course") Course course);

}
