package com.example.nhom3_tt_.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderCreationRequest {
  String listProduct;
  public OrderCreationRequest(String listProduct) {
    this.listProduct = listProduct;
  }

  // Getter và Setter (nếu không dùng @Data của Lombok)
  public String getListProduct() {
    return listProduct;
  }

  public void setListProduct(String listProduct) {
    this.listProduct = listProduct;
  }

}
