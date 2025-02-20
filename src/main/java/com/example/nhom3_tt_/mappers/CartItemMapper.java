package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.CartItemRequest;
import com.example.nhom3_tt_.dtos.response.CartItemResponse;
import com.example.nhom3_tt_.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

  CartItem convertToEntity(CartItemRequest cartItemRequest);

  @Mapping(source = "cart.id", target = "cartId")
  @Mapping(source = "course.id", target = "courseId")
  CartItemResponse toCartItemResponse(CartItem cartItem);
}
