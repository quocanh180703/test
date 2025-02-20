package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.CategoryRequest;
import com.example.nhom3_tt_.dtos.response.CategoryResponse;
import com.example.nhom3_tt_.dtos.response.CourseResponse;
import com.example.nhom3_tt_.models.Category;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CategoryService {

  List<CategoryResponse> getAllCategories();

  CategoryResponse getCategoryById(Long id);

  @PreAuthorize("hasAnyAuthority('ADMIN')")
  CategoryResponse updateCategory(Long id, CategoryRequest categoryDetails);

  @PreAuthorize("hasAnyAuthority('ADMIN')")
  void deleteCategory(Long id);

  @PreAuthorize("hasAnyAuthority('ADMIN')")
  CategoryResponse createCategory(CategoryRequest categoryRequest);
}
