package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.response.OrderResponse;
import com.example.nhom3_tt_.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {UserMapper.class})
public interface OrderMapper {

  @Mapping(target = "student", source = "student")
  // Không ánh xạ toàn bộ OrderDetail, chỉ giữ danh sách ID
  @Mapping(
      target = "orderDetailIds",
      expression =
          "java(order.getOrderDetails() != null ? order.getOrderDetails().stream().map(com.example.nhom3_tt_.models.OrderDetail::getId).toList() : java.util.Collections.emptyList())")
  OrderResponse toOrderResponse(Order order);
}
