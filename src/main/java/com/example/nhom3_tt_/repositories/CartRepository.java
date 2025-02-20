package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByStudentId(Long userId);
}
