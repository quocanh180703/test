package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String code;

  @Column(name = "begin_day", nullable = false)
  private LocalDateTime beginDay;

  @Column(name = "expire_day", nullable = false)
  private LocalDateTime expireDay;

  @Column(name = "percent_discount", nullable = false)
  private double percentDiscount;

  @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CouponUsed> couponUsedList;

  public boolean isValid() {
    LocalDateTime now = LocalDateTime.now();
    return now.isAfter(beginDay) && now.isBefore(expireDay);
  }
}
