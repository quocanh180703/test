package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
  Optional<Section> findById(Long id);

  List<Section> findAllByCourseId(Long courseId);
}
