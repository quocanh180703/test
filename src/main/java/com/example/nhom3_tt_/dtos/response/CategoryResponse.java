package com.example.nhom3_tt_.dtos.response;

import com.example.nhom3_tt_.models.Category;
import com.example.nhom3_tt_.models.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private List<CourseResponse> courses;

    public static CategoryResponse map(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }

}