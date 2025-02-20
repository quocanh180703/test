package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.CouponRequest;
import com.example.nhom3_tt_.dtos.response.CouponResponse;
import com.example.nhom3_tt_.exception.CustomException;
import com.example.nhom3_tt_.exception.NotFoundException;
import com.example.nhom3_tt_.mappers.CouponMapper;
import com.example.nhom3_tt_.models.Coupon;
import com.example.nhom3_tt_.repositories.CouponRepository;
import com.example.nhom3_tt_.services.CouponService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService {

  private final CouponRepository couponRepository;
  private final CouponMapper couponMapper;

  @Transactional
  @Override
  public CouponResponse create(CouponRequest couponRequest) {
    // kiểm tra ngày
    if (couponRequest.getExpireDay().isBefore(couponRequest.getBeginDay())) {
      throw new CustomException(
          "ExpiredDate cannot before BeginDate", HttpStatus.BAD_REQUEST.value());
    }

    // kiểm tra code đã tồn tại chưa
    Optional<Coupon> existedCodeCoupon =
        couponRepository.findByCodeIgnoreCase(couponRequest.getCode());
    if (existedCodeCoupon.isPresent()) {
      throw new CustomException("Coupon code is already existed!", HttpStatus.BAD_REQUEST.value());
    }

    Coupon coupon = couponMapper.convertToEntity(couponRequest);
    return couponMapper.convertToResponse(couponRepository.save(coupon));
  }

  @Override
  public List<CouponResponse> getAll(Pageable pageable) {
    return couponRepository.findAll(pageable).stream()
        .map(couponMapper::convertToResponse)
        .toList();
  }

  @Override
  public List<CouponResponse> getUnexpireds(Pageable pageable) {
    LocalDateTime now = LocalDateTime.now();
    return couponRepository.findCouponUnexpired(now, pageable).stream()
        .map(couponMapper::convertToResponse)
        .toList();
  }

  @Override
  public CouponResponse getById(Long id) {
    Coupon coupon =
        couponRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Coupon not found with id=" + id));
    return couponMapper.convertToResponse(coupon);
  }

  @Override
  public CouponResponse getByCode(String code) {
    return couponMapper.convertToResponse(
        couponRepository
            .findByCodeIgnoreCase(code)
            .orElseThrow(() -> new NotFoundException("Coupon cannot found with code=" + code)));
  }

  @Transactional
  @Override
  public CouponResponse update(Long id, CouponRequest newCoupon) {
    // kiểm tra ngày
    if (newCoupon.getExpireDay().isBefore(newCoupon.getBeginDay())) {
      throw new CustomException(
          "ExpiredDate cannot before BeginDate", HttpStatus.BAD_REQUEST.value());
    }

    // nếu mã coupon mới khác với mã coupon hiện tại thì check xem mã đó có tồn tại chưa
    Coupon existingCoupon =
        couponRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Coupon not found with id=" + id));

    if (!existingCoupon.getCode().equals(newCoupon.getCode())) {
      Optional<Coupon> existedCodeCoupon =
          couponRepository.findByCodeIgnoreCase(newCoupon.getCode());
      if (existedCodeCoupon.isPresent()) {
        throw new CustomException(
            "Coupon code is already existed!", HttpStatus.BAD_REQUEST.value());
      }
    }

    existingCoupon.setCode(newCoupon.getCode());
    existingCoupon.setBeginDay(newCoupon.getBeginDay());
    existingCoupon.setExpireDay(newCoupon.getExpireDay());
    existingCoupon.setPercentDiscount(newCoupon.getPercentDiscount());

    return couponMapper.convertToResponse(couponRepository.save(existingCoupon));
  }

  @Transactional
  @Override
  public String forceDelte(Long id) {
    boolean isExistedCoupon = couponRepository.existsById(id);
    if (!isExistedCoupon) {
      throw new NotFoundException("Coupon nou found with id=" + id);
    }
    couponRepository.deleteById(id);
    return "Force delete Coupon successfully with id=" + id;
  }
}
