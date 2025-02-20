package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.CartItemRequest;
import com.example.nhom3_tt_.dtos.response.CartItemResponse;
import com.example.nhom3_tt_.exception.CustomException;
import com.example.nhom3_tt_.mappers.CartItemMapper;
import com.example.nhom3_tt_.models.*;
import com.example.nhom3_tt_.repositories.CartItemRepository;
import com.example.nhom3_tt_.repositories.CartRepository;
import com.example.nhom3_tt_.repositories.CourseRepository;
import com.example.nhom3_tt_.repositories.EnrollRepository;
import com.example.nhom3_tt_.services.CartItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImp implements CartItemService {

  private final CartItemRepository cartItemRepository;
  private final CartRepository cartRepository;
  private final CourseRepository courseRepository;
  private final CartItemMapper cartItemMapper;
  private final EnrollRepository EnrollRepository;

  // [#1.1 POST METHOD]: this method only add cart item to cart of current user, by authorize user
  public CartItemResponse addCartItem(CartItemRequest cartItemRequest) {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Long currentUserId = currentUser.getId();

    Cart existingCart = cartRepository.findByStudentId(currentUserId)
        .orElseThrow(() -> new CustomException("Cart not found", 404));

    Long courseId = cartItemRequest.getCourseId();
    Long cartId = existingCart.getId();

    Course existingCourse =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new CustomException("Course not found", 404));

    CartItem existingCartItem = cartItemRepository.getCartItemByCartIdAndCourseId(cartId, courseId);

    if (existingCartItem != null) {
      throw new CustomException("This item is already exists in this cart", 409);
    }

    Enroll existingEnroll = EnrollRepository.getEnrollByStudentAndCourseId(currentUser, existingCourse);
    
    if (existingEnroll != null) {
      throw new CustomException("This item is already enrolled by this user", 409);
    }

    CartItem cartItem = CartItem.builder().cart(existingCart).course(existingCourse).build();

    CartItem savedCartItem = cartItemRepository.save(cartItem);
    return cartItemMapper.toCartItemResponse(savedCartItem);
  }

  // [#1.2 POST METHOD]: this create method can add cart item for any cart's user, for quick test api
  @Override
  public CartItemResponse createCartItem(Long cartId, Long courseId) {
    Cart existingCart =
        cartRepository
            .findById(cartId)
            .orElseThrow(() -> new CustomException("Cart not found", 404));

    Course existingCourse =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new CustomException("Course not found", 404));

    CartItem existingCartItem = cartItemRepository.getCartItemByCartIdAndCourseId(cartId, courseId);

    if (existingCartItem != null) {
      throw new CustomException("This item is already exists in this cart", 409);
    }

    CartItem cartItem = CartItem.builder().cart(existingCart).course(existingCourse).build();

    CartItem savedCartItem = cartItemRepository.save(cartItem);
    return cartItemMapper.toCartItemResponse(savedCartItem);
  }

  // [#2.1 SAME GET METHOD]: this method is get all cart items of current user, by authorize user
  public List<CartItemResponse> getAllCartItemsByOwn(Pageable pageable) {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Long currentUserId = currentUser.getId();

    Cart existingCart = cartRepository.findByStudentId(currentUserId)
        .orElseThrow(() -> new CustomException("Cart not found", 404));

    List<CartItem> cartItems = cartItemRepository.findAllCartItemsByCartId(existingCart.getId(),
        pageable);
    return cartItems.stream().map(cartItemMapper::toCartItemResponse).toList();
  }

  // [#2.2 SAME GET METHOD]: this method can get any cart's user, for test api
  @Override
  public List<CartItemResponse> getAllCartItemsByCartId(Long cartId, Pageable pageable) {
    List<CartItem> cartItems = cartItemRepository.findAllCartItemsByCartId(cartId, pageable);
    return cartItems.stream().map(cartItemMapper::toCartItemResponse).toList();
  }

  public List<CartItemResponse> getAllCartItems(Pageable pageable) {
    List<CartItem> cartItems = cartItemRepository.findAll(pageable).getContent();
    return cartItems.stream().map(cartItemMapper::toCartItemResponse).toList();
  }

  @Override
  public CartItemResponse deleteCartItem(Long cartItemId) {
    CartItem existingCartItem =
        cartItemRepository
            .findById(cartItemId)
            .orElseThrow(
                () -> new CustomException("Cart Item not found", 404));

    CartItemResponse cartItemResponse = cartItemMapper.toCartItemResponse(existingCartItem);
    cartItemRepository.deleteById(cartItemId);
    return cartItemResponse;
  }
}
