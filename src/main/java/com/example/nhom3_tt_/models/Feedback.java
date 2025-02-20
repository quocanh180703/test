package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false) // Foreign key liên kết với User
  private User student;

  @Column(nullable = false, length = 500) // Giới hạn độ dài comment
  private String comment;
}
