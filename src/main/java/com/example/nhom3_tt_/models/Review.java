package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "course_id", nullable = false) // Liên kết tới Course
  private Course course;

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false) // Liên kết tới Student (User)
  private User student;

  @Column(nullable = false)
  private double star;

  @Column(length = 500)
  private String comment;
}
