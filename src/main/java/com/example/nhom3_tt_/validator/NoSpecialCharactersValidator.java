package com.example.nhom3_tt_.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSpecialCharactersValidator
    implements ConstraintValidator<NoSpecialCharacters, String> {

  private static final String SPECIAL_CHARACTERS_REGEX =
      "^(?!.*[\\p{So}])[\\w\\u3000-\\u303F\\u3040-\\u309F\\u30A0-\\u30FF\\uFF00-\\uFFEF\\u4E00-\\u9FAF\\u2605-\\u2606\\u2190-\\u2195\\u203B\\u00C0-\\u1EF9\\s]*$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // Không cần kiểm tra giá trị null (đã được @NotBlank xử lý)
    }
    return value.matches(SPECIAL_CHARACTERS_REGEX);
  }
}
