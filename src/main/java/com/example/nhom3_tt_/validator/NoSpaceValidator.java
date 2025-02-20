package com.example.nhom3_tt_.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSpaceValidator implements ConstraintValidator<NoSpace, String> {

  private static final String SPACE_REGEX = "^(?!.*\\s).*$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // Không cần kiểm tra giá trị null (đã được @NotBlank xử lý)
    }
    return value.matches(SPACE_REGEX);
  }
}
