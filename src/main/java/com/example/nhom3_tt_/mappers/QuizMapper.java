package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.quiz.QuizRequest;
import com.example.nhom3_tt_.dtos.response.quiz.QuizResponse;
import com.example.nhom3_tt_.models.Quiz;
import com.example.nhom3_tt_.models.Section;
import org.mapstruct.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface QuizMapper {

  @Mapping(target = "section", source = "sectionId", qualifiedByName = "mapSection")
  @Mapping(target = "timeLimit", source = "timeLimit", qualifiedByName = "validateTimeLimit")
  @Mapping(target = "attemptAllowed", source = "attemptAllowed", qualifiedByName = "validateAttemptAllowed")
  Quiz toQuiz(QuizRequest quizRequest);

  @Mapping(target = "sectionId", source = "section.id")
  QuizResponse toQuizResponse(Quiz quiz);

  @Named("mapSection")
  default Section mapSection(Long sectionId) {
    if (sectionId == null) {
      return null;
    }
    Section section = new Section();
    section.setId(sectionId);
    return section;
  }

  @Named("validateTimeLimit")
  default int validateTimeLimit(int timeLimit) {
    if (timeLimit <= 0) {
      throw new IllegalArgumentException("Time limit must be greater than 0");
    }
    return timeLimit;
  }

  @Named("validateAttemptAllowed")
  default int validateAttemptAllowed(int attemptAllowed) {
    if (attemptAllowed <= 0) {
      throw new IllegalArgumentException("Attempt allowed must be greater than 0");
    }
    return attemptAllowed;
  }
}