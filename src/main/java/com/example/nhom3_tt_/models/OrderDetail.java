package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  private Double price; // giá lấy từ khoá học

  // hàm tính giá (chỉ dùng khi có course)
  public void calculateAndSetPrice() {
    if (course == null) {
      throw new IllegalStateException("Course must not be null to calculate price.");
    }
    this.price = Math.max(0.0, course.getRegularPrice());
  }
}
