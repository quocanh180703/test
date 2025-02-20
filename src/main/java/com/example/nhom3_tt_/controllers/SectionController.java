package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.section.SectionRequest;
import com.example.nhom3_tt_.mappers.SectionMapper;
import com.example.nhom3_tt_.services.LectureService;
import com.example.nhom3_tt_.services.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
public class SectionController {
  private final SectionService sectionService;
  private final LectureService lectureService;
  private final SectionMapper mapper;

  @GetMapping("")
  public ResponseEntity<?> getAllSections() {
    return ResponseEntity.ok(sectionService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getSectionById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(sectionService.getById(id));
  }

  @GetMapping("/{id}/lectures")
  public ResponseEntity<?> getLecturesBySectionId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(lectureService.getAllBySectionId(id));
  }

  @PostMapping("")
  public ResponseEntity<?> createSection(@RequestBody @Valid SectionRequest req) {
    return ResponseEntity.ok(sectionService.create(mapper.toSection(req)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateSection(
      @PathVariable("id") Long id, @RequestBody @Valid SectionRequest req) {
    return ResponseEntity.ok(sectionService.update(id, mapper.toSection(req)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteSection(@PathVariable("id") Long id) {
    sectionService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
