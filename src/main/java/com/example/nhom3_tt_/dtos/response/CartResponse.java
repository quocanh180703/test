package com.example.nhom3_tt_.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

  private Long id;
  private Long studentId;
  private List<CartItemResponse> cartItems;
}
