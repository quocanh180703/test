package com.example.nhom3_tt_.models.embeddedId;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptionId implements Serializable {

  private Long questionId;
  private Long id;

  public QuestionOptionId(Long questionId, long l) {
  }
}
