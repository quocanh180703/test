package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lectures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecture extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "section_id", nullable = false) // Liên kết với Section
  private Section section;

  @Column(nullable = false)
  private String title;

  @Column(name = "description", length = 500)
  private String description;

  @Column(nullable = false)
  private boolean preview;

  @Column(name = "link_video")
  private String linkVideo;

  @Column(name = "thumbnail")
  private String thumbnail;
}
