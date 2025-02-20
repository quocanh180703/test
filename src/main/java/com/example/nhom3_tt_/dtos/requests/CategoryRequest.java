package com.example.nhom3_tt_.dtos.requests;

import com.example.nhom3_tt_.validator.NoSpecialCharacters;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
  @NotBlank(message = "Category name cannot be blank")
  @Size(max = 255, message = "Category name must not exceed 255 characters")
  @NoSpecialCharacters
  private String name;
}
