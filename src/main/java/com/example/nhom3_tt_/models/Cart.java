package com.example.nhom3_tt_.models;

import com.example.nhom3_tt_.auditing.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "student_id", nullable = false, unique = true) // Một student chỉ có một cart
  @JsonIgnore
  private User student;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItem> cartItems;

  @Override
  public String toString() {
    return "Cart{id="
        + id
        + ", studentId="
        + (student != null ? student.getId() : "null")
        + ", cartItemsCount="
        + (cartItems != null ? cartItems.size() : 0)
        + "}";
  }
}
