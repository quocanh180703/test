package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Question extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
  private Long id;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<QuestionOption> options;

  private Long answer;

  @ManyToOne
  @JoinColumn(name = "quiz_id", referencedColumnName = "id")
  private Quiz quiz;

  @OneToMany(
      mappedBy = "question",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.LAZY)
  private List<QuizSubmissionAnswer> quizSubmissionAnswers;
}
