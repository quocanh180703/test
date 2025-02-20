package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
    name = "coupon_used",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "coupon_id"})
      // Ensures one user cannot use the same coupon twice
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponUsed extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "coupon_id", nullable = false)
  private Coupon coupon;

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  private User student;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "used_at", nullable = false, updatable = false)
  private Date usedAt;
}
