package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.Cart;
import com.example.nhom3_tt_.models.CartItem;
import com.example.nhom3_tt_.models.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  List<CartItem> findAllCartItemsByCartId(Long cartId, Pageable pageable);

  CartItem getCartItemByCartIdAndCourseId(Long cartId, Long courseId);

  Optional<CartItem> findByCourseAndCart(Course course, Cart cart);

  void deleteAllByCart(Cart cart);
}
