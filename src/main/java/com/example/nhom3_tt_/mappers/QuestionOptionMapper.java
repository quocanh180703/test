package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.response.quiz.QuestionOptionResponse;
import com.example.nhom3_tt_.models.QuestionOption;
import com.example.nhom3_tt_.models.embeddedId.QuestionOptionId;
import org.mapstruct.Mapper;

@Mapper
public interface QuestionOptionMapper {
  QuestionOptionResponse toQuestionOptionReponse(QuestionOption questionOption);

  default Long map(QuestionOptionId value) {
    return value != null ? value.getId() : null;
  }
}
