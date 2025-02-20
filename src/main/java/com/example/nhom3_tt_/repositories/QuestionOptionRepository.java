package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.QuestionOption;
import com.example.nhom3_tt_.models.embeddedId.QuestionOptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, QuestionOptionId> {
  List<QuestionOption> findAllByQuestionId(Long questionId);
}
