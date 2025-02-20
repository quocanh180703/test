package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.domain.RestResponse;
import com.example.nhom3_tt_.dtos.requests.CategoryRequest;
import com.example.nhom3_tt_.dtos.response.CategoryResponse;
import com.example.nhom3_tt_.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("")
  public ResponseEntity<?> getAllCategories() {
    List<CategoryResponse> categoryResponses = categoryService.getAllCategories();
    return ResponseEntity.ok().body(categoryResponses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
    CategoryResponse categoryResponse = categoryService.getCategoryById(id);
    return ResponseEntity.ok().body(categoryResponse);
  }

  @PostMapping("")
  public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
    CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
    return ResponseEntity.status(201).body(categoryResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCategory(
      @PathVariable("id") Long id, @Valid @RequestBody CategoryRequest categoryRequest) {

    CategoryResponse categoryResponse = categoryService.updateCategory(id, categoryRequest);
    return ResponseEntity.status(200).body(categoryResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.status(200)
        .body(new ObjectMapper().createObjectNode().put("message", "Delete category successfully"));
  }
}
