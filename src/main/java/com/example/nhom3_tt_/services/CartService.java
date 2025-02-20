package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.response.CartResponse;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CartService {

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  List<CartResponse> getAllCarts();

  CartResponse getCartById(Long cartId);

  CartResponse createCart(Long studentId);

  CartResponse deleteCart(Long cartId);

  CartResponse getOrCreateCartByStudentId(Long studentId);

  public Double calculatePriceWithCoupons(Long cartId, List<String> couponCodes);
}
