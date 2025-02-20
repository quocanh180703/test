package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.response.CartResponse;
import com.example.nhom3_tt_.dtos.response.CouponResponse;
import com.example.nhom3_tt_.exception.NotFoundException;
import com.example.nhom3_tt_.mappers.CartMapper;
import com.example.nhom3_tt_.models.Cart;
import com.example.nhom3_tt_.models.CartItem;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.CartRepository;
import com.example.nhom3_tt_.repositories.UserRepository;
import com.example.nhom3_tt_.services.CartService;
import com.example.nhom3_tt_.services.CouponService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final UserRepository userRepository;
  private final CartMapper cartMapper;
  private final CouponService couponService;

  @Override
  public List<CartResponse> getAllCarts() {
    List<Cart> carts = cartRepository.findAll();
    return carts.stream().map(cartMapper::toCartResponse).toList();
  }

  @Override
  public CartResponse getCartById(Long cartId) {
    Cart cart =
        cartRepository
            .findById(cartId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
    return cartMapper.toCartResponse(cart);
  }

  @Override
  public CartResponse createCart(Long studentId) {
    Optional<User> studentOptional = userRepository.findById(studentId);
    if (studentOptional.isEmpty()) {
      throw new IllegalArgumentException("Student not found");
    }
    User student = studentOptional.get();

    Cart cart = Cart.builder().student(student).build();

    Cart savedCart = cartRepository.save(cart);
    return cartMapper.toCartResponse(savedCart);
  }

  @Override
  public CartResponse deleteCart(Long cartId) {
    Cart cart =
        cartRepository
            .findById(cartId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
    cartRepository.delete(cart);
    return cartMapper.toCartResponse(cart);
  }

  @Override
  public CartResponse getOrCreateCartByStudentId(Long studentId) {
    User student1 =
        userRepository
            .findById(studentId)
            .orElseThrow(
                () -> new IllegalArgumentException("Student not found with id: " + studentId));

    Cart cart =
        cartRepository
            .findByStudentId(studentId)
            .orElseGet(
                () -> {
                  User student =
                      userRepository
                          .findById(studentId)
                          .orElseThrow(
                              () ->
                                  new IllegalArgumentException(
                                      "Student not found with id: " + studentId));

                  Cart newCart =
                      Cart.builder()
                          .student(student)
                          .cartItems(List.of()) // Khởi tạo danh sách rỗng
                          .build();

                  return cartRepository.save(newCart);
                });

    return cartMapper.toCartResponse(cart);
  }

  @Override
  public Double calculatePriceWithCoupons(Long cartId, List<String> couponCodes) {
    Cart cart =
        cartRepository
            .findById(cartId)
            .orElseThrow(() -> new NotFoundException("Cart not found with id = " + cartId));
    double totalPrice = 0.0;

    // Tính tổng giá trị giỏ hàng
    for (CartItem item : cart.getCartItems()) {
      double price = item.getCourse().getRegularPrice();
      totalPrice += price;
    }

    // Nếu không có mã giảm giá, trả về tổng giá bình thường
    if (couponCodes == null || couponCodes.isEmpty()) {
      return totalPrice;
    }

    // Áp dụng từng mã giảm giá từ danh sách
    for (String couponCode : couponCodes) {
      CouponResponse coupon = couponService.getByCode(couponCode);
      double discountPercent = coupon.getPercentDiscount();
      totalPrice -= totalPrice * (discountPercent / 100);
    }

    return totalPrice;
  }
}
