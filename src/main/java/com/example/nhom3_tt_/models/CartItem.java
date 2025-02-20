package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CartItem extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cart_id", nullable = false) // Liên kết tới giỏ hàng
  @JsonIgnore
  private Cart cart;

  @ManyToOne
  @JoinColumn(name = "course_id", nullable = false) // Liên kết tới khóa học
  private Course course;
}
