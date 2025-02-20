package com.example.nhom3_tt_.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nhom3_tt_.models.Lecture;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
  List<Lecture> findAllBySectionId(Long sectionId);
}
