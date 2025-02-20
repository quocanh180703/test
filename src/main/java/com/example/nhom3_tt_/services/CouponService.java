package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.CouponRequest;
import com.example.nhom3_tt_.dtos.response.CouponResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CouponService {

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  CouponResponse create(CouponRequest couponRequest);

  List<CouponResponse> getAll(Pageable pageable);

  List<CouponResponse> getUnexpireds(Pageable pageable);

  CouponResponse getById(Long id);

  CouponResponse getByCode(String code);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  CouponResponse update(Long id, CouponRequest newCoupon);

  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  String forceDelte(Long id);
}
