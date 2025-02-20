package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.OrderDetailRequest;
import com.example.nhom3_tt_.dtos.response.OrderDetailResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface OrderDetailService {
  OrderDetailResponse create(OrderDetailRequest orderDetailRequest);

  @PreAuthorize("hasAuthority('ADMIN')")
  List<OrderDetailResponse> getAll(Pageable pageable);

  OrderDetailResponse getById(Long id);

  @PreAuthorize("hasAuthority('ADMIN')")
  List<OrderDetailResponse> getByStudentId(Long studentId);

  List<OrderDetailResponse> getByOrderId(Long id);

  List<OrderDetailResponse> myOrderDetails();

  OrderDetailResponse update(Long id, OrderDetailRequest newOrderDetail);

  @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
  OrderDetailResponse forceDelete(Long id);
}
