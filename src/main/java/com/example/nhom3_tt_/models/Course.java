package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "instructor_id", nullable = false) // Foreign key liên kết tới User
  private User instructor;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false) // Liên kết tới Category
  @JsonIgnore
  private Category category;

  @Column(nullable = false)
  private String title;

  @Column(name = "short_desc", length = 255)
  private String shortDescription;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ETypeLevel level;

  @Column(name = "regular_price", nullable = false)
  private double regularPrice = 0;

  @Temporal(TemporalType.DATE)
  @Column(name = "publish_day", nullable = false)
  private Date publishDay;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ECourseStatus status;

  private String language;

  private String thumbnail;

  @Column(name = "require_login", nullable = false)
  private boolean requireLogin;

  @Column(name = "intro_video")
  private String introVideo;

  private String requirement;

  private String objective;

  @Column(name = "description")
  private String description;

  @Column(name = "close_caption")
  private String closeCaption;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Section> sections;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Enroll> enrollments;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItem> cartItems;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderDetail> orderDetails;
}
