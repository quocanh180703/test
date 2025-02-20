package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.CartItemRequest;
import com.example.nhom3_tt_.dtos.response.CartItemResponse;
import com.example.nhom3_tt_.services.CartItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartItemController {

  private final CartItemService cartItemService;

  // this API only add cart item to cart of current user, by authorize user
  @PostMapping("/carts/items")
  public ResponseEntity<CartItemResponse> addCartItem(
      @RequestBody CartItemRequest cartItemRequest) {
    return ResponseEntity.ok(cartItemService.addCartItem(cartItemRequest));
  }

  // this API can add cart item for any cart's user, for quick test api
  @PostMapping("/carts/{cartId}/items/{courseId}")
  public ResponseEntity<CartItemResponse> createCartItem(@PathVariable Long cartId,
      @PathVariable Long courseId) {
    return ResponseEntity.ok(cartItemService.createCartItem(cartId, courseId));
  }

  // Get all cart items in cart's current user
  @GetMapping("/carts/items/me")
  public ResponseEntity<List<CartItemResponse>> getAllCartItemsByOwn(
      @ParameterObject Pageable pageable) {
    return ResponseEntity.ok(cartItemService.getAllCartItemsByOwn(pageable));
  }

  @GetMapping("/carts/items")
  public ResponseEntity<List<CartItemResponse>> getAllCartItems(
      @ParameterObject Pageable pageable) {
    return ResponseEntity.ok(cartItemService.getAllCartItems(pageable));
  }

  @GetMapping("/carts/{cartId}/items")
  public ResponseEntity<List<CartItemResponse>> getCartItemsByCartId(@PathVariable Long cartId,
      @ParameterObject Pageable pageable) {
    return ResponseEntity.ok(cartItemService.getAllCartItemsByCartId(cartId, pageable));
  }

  @DeleteMapping("/items/{cartItemId}")
  public ResponseEntity<CartItemResponse> deleteCartItem(@PathVariable Long cartItemId) {
    return ResponseEntity.ok(cartItemService.deleteCartItem(cartItemId));
  }
}
