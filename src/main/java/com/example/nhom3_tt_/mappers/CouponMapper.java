package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.CouponRequest;
import com.example.nhom3_tt_.dtos.response.CouponResponse;
import com.example.nhom3_tt_.models.Coupon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponMapper {

  Coupon convertToEntity(CouponRequest couponRequest);
  
  CouponResponse convertToResponse(Coupon coupon);
}
