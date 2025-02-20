package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.Category;
import com.example.nhom3_tt_.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCoursesContaining(Course course);
    List<Category> findByCourses_Id(Long courseId);
}
