package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.Category;
import com.example.nhom3_tt_.models.Course;
import com.example.nhom3_tt_.models.ECourseStatus;
import com.example.nhom3_tt_.models.ETypeLevel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
  List<Course> findByCategory(Category category);

  Optional<Course> findById(Long id);

  Page<Course> findAll(Pageable pageable);

  @Query("SELECT c FROM Course c WHERE c.status = :status")
  Page<Course> findByStatus(@Param("status") ECourseStatus status, Pageable pageable);

  @Query("SELECT c FROM Course c WHERE c.level = :level")
  Page<Course> findByLevel(@Param("level") ETypeLevel level, Pageable pageable);

  @Query("SELECT c FROM Course c WHERE c.category.id = :categoryId")
  Page<Course> findByCategoryId(Long categoryId, Pageable pageable);

  @Query("SELECT c FROM Course c WHERE c.instructor.id = :instructorId")
  Page<Course> findByInstructorId(Long instructorId, Pageable pageable);

  @Query("SELECT c FROM Course c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))")
  Page<Course> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
}
