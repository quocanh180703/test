package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.CategoryRequest;
import com.example.nhom3_tt_.dtos.response.CategoryResponse;
import com.example.nhom3_tt_.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface CategoryMapper {

  @Mappings({@Mapping(source = "name", target = "name")})
  Category convertToEntity(CategoryRequest categoryRequest);

  @Mappings({
          @Mapping(source = "id", target = "id"),
          @Mapping(source = "name", target = "name"),
          @Mapping(source = "courses", target = "courses"),
  })
  CategoryResponse convertToResponse(Category category);
}
