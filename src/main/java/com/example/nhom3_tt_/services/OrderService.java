package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.OrderDetailRequest;
import com.example.nhom3_tt_.dtos.response.OrderDetailResponse;
import com.example.nhom3_tt_.dtos.response.OrderResponse;
import com.example.nhom3_tt_.dtos.response.earningAnalytic.EarningAnalyticResponse;
import com.example.nhom3_tt_.models.Order;
import com.example.nhom3_tt_.models.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface OrderService {
  String getAmount(User user, List<String> couponCodes, String listProduct);

  OrderResponse createOrder(User user, String vnp_Amount, String listProduct);

  OrderResponse getOrderById(Long orderId);

  Order getOrderEntityById(Long orderId);

  List<OrderResponse> getAllOrdersByStudentId(Long studentId);

  OrderDetailResponse addOrderDetail(OrderDetailRequest orderDetailRequest);

  void deleteOrderByOrderId(Long orderId);

  void deleteOrderDetailById(Long orderDetailId);

  @PreAuthorize("hasAuthority('ADMIN')")
  List<EarningAnalyticResponse> getEarning();

  @PreAuthorize("hasAuthority('ADMIN')")
  Double getEarningTotal();

  @PreAuthorize("hasAuthority('ADMIN')")
  Double getEarningByDay(String date);

  @PreAuthorize("hasAuthority('ADMIN')")
  Double getEarningByMonth(String month);

  @PreAuthorize("hasAuthority('ADMIN')")
  Double getEarningByYear(String year);
}
