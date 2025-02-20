package com.example.nhom3_tt_.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = NoSpecialCharactersValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpecialCharacters {

  String message() default "Field must not contain special characters or emojis except _";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
