package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class QuizSubmission extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
  private Long id;

  private Double score;

  @Column(name = "total_times")
  private int totalTimes;

  @Column private int totalCorrects;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "quiz_id", referencedColumnName = "id")
  private Quiz quiz;

  @OneToMany(
      mappedBy = "quizSubmission",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.LAZY)
  private List<QuizSubmissionAnswer> answers;
}
