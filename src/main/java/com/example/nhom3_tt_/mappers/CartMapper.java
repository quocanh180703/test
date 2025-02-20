package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.response.CartItemResponse;
import com.example.nhom3_tt_.dtos.response.CartResponse;
import com.example.nhom3_tt_.models.Cart;
import com.example.nhom3_tt_.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

  @Mapping(source = "student.id", target = "studentId")
  @Mapping(source = "cartItems", target = "cartItems") // Không cần dùng qualifiedByName
  CartResponse toCartResponse(Cart cart);

  List<CartResponse> toCartResponseList(List<Cart> carts);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "course.id", target = "courseId")
  @Mapping(source = "cart.id", target = "cartId")
  CartItemResponse toCartItemResponse(CartItem cartItem);
}
