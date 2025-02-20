package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.OrderDetailRequest;
import com.example.nhom3_tt_.dtos.response.OrderDetailResponse;
import com.example.nhom3_tt_.models.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {CourseMapper.class, OrderMapper.class})
public interface OrderDetailMapper {

  @Mapping(source = "orderId", target = "order.id")
  @Mapping(source = "courseId", target = "course.id")
  @Mapping(target = "price", ignore = true)
  OrderDetail convertToEntity(OrderDetailRequest orderDetailRequest);

  @Mapping(source = "order.id", target = "orderId")
  @Mapping(source = "course", target = "course")
  OrderDetailResponse convertToResponse(OrderDetail orderDetail);
}
