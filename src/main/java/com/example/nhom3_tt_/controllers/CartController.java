package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.exception.CustomException;
import com.example.nhom3_tt_.services.CartService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

//  @GetMapping("/carts")
//  public ResponseEntity<?> getAllCarts() {
//    return ResponseEntity.ok(cartService.getAllCarts());
//  }

  @GetMapping("/carts/{cartId}")
  public ResponseEntity<?> getCartById(@PathVariable Long cartId) {
    return ResponseEntity.ok(cartService.getCartById(cartId));
  }

  @PostMapping("/carts/student/{studentId}")
  public ResponseEntity<?> getOrCreateCartByStudentId(@PathVariable Long studentId) {
    return ResponseEntity.ok(cartService.getOrCreateCartByStudentId(studentId));
  }

//  @PostMapping("/carts/{studentId}")
//  public ResponseEntity<?> createCart(@PathVariable Long studentId) {
//    return ResponseEntity.ok(cartService.createCart(studentId));
//  }

  //  @DeleteMapping("/carts/{cartId}")
  //  public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
  //    return ResponseEntity.ok(cartService.deleteCart(cartId));
  //  }

  @PostMapping("/carts/{id}/get-price")
  public ResponseEntity<?> getCartPrice(
      @PathVariable("id") Long id, @RequestParam(required = false) List<String> couponCodes) {

    boolean hasCoupon = couponCodes != null && !couponCodes.isEmpty();
    // kiểm tra trùng lặp trong couponCodes
    if (hasCoupon) {
      Set<String> uniqueCoupons = new HashSet<>(couponCodes);
      if (uniqueCoupons.size() != couponCodes.size()) {
        throw new CustomException(
            "Duplicate coupon codes are not allowed", HttpStatus.BAD_REQUEST.value());
      }
    }
    // tính toán giá mới sau khi áp dụng danh sách coupon
    Double totalPrice =
        hasCoupon
            ? cartService.calculatePriceWithCoupons(id, couponCodes)
            : cartService.calculatePriceWithCoupons(id, null);

    return ResponseEntity.ok(
        Map.of("totalPrice", totalPrice, "appliedCoupons", hasCoupon ? couponCodes : ""));
  }
}
