package com.example.nhom3_tt_.mappers;


import com.example.nhom3_tt_.dtos.response.earningAnalytic.EarningAnalyticResponse;
import com.example.nhom3_tt_.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EarningMapper {
  @Mapping(target = "student.cart", ignore = true)
  @Mapping(target = "student.orders", ignore = true)
  @Mapping(target = "student.couponUsedList", ignore = true)
  @Mapping(target = "student.feedbackList", ignore = true)
  @Mapping(target = "student.enrollments", ignore = true)
  @Mapping(target = "student.reviews", ignore = true)
  @Mapping(target = "student.subscriptions", ignore = true)
  @Mapping(target = "student.subscribers", ignore = true)
  EarningAnalyticResponse toEarningAnalyticResponse(Order order);
}
