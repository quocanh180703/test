package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.CategoryRequest;
import com.example.nhom3_tt_.dtos.response.CategoryResponse;
import com.example.nhom3_tt_.exception.NotFoundException;
import com.example.nhom3_tt_.mappers.CategoryMapper;
import com.example.nhom3_tt_.models.Category;
import com.example.nhom3_tt_.repositories.CategoryRepository;
import com.example.nhom3_tt_.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Transactional
  @Override
  public CategoryResponse createCategory(CategoryRequest categoryRequest) {
    Category category = categoryMapper.convertToEntity(categoryRequest);
    category.setName(categoryRequest.getName());
    Category savedCategory = categoryRepository.save(category);
    return categoryMapper.convertToResponse(savedCategory);
  }

  @Override
  public List<CategoryResponse> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(categoryMapper::convertToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public CategoryResponse getCategoryById(Long id) {
    Category category =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Cannot find category with ID: " + id));
    List<Long> courseIds =
        category.getCourses().stream().map(c -> c.getId()).collect(Collectors.toList());

    log.info("Course IDs: {}", courseIds);
    return categoryMapper.convertToResponse(category);
  }

  @Transactional
  @Override
  public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
    Category category =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Cập nhật không thành công với ID: " + id));
    category.setName(categoryRequest.getName());
    Category updatedCategory = categoryRepository.save(category);
    return categoryMapper.convertToResponse(updatedCategory);
  }

  @Transactional
  @Override
  public void deleteCategory(Long id) {
    Category category =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Xóa không thành công với ID: " + id));
    categoryRepository.delete(category);
  }
}
