package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.models.embeddedId.QuestionOptionId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOption {
  @EmbeddedId private QuestionOptionId id;

  private String content;
  private int seq;

  @ManyToOne
  @MapsId("questionId")
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;
}
