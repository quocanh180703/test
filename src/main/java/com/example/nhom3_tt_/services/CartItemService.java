package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.CartItemRequest;
import com.example.nhom3_tt_.dtos.response.CartItemResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartItemService {

  CartItemResponse addCartItem(CartItemRequest cartItemRequest);

  List<CartItemResponse> getAllCartItems(Pageable pageable);

  List<CartItemResponse> getAllCartItemsByOwn(Pageable pageable);

  List<CartItemResponse> getAllCartItemsByCartId(Long cartId, Pageable pageable);

  CartItemResponse createCartItem(Long CartId, Long CourseId);

  CartItemResponse deleteCartItem(Long CartItemId);
}
