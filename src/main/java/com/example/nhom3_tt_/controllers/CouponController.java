package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.CouponRequest;
import com.example.nhom3_tt_.services.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {
  private final CouponService couponService;

  @PostMapping("")
  public ResponseEntity<?> create(@Valid @RequestBody CouponRequest couponRequest) {
    return ResponseEntity.ok(couponService.create(couponRequest));
  }

  @GetMapping("")
  public ResponseEntity<?> getAll(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(couponService.getAll(pageable));
  }

  // get all (begin & expire day: in future)
  @GetMapping("/unexpireds")
  public ResponseEntity<?> getUnexpireds(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(couponService.getUnexpireds(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getByCode(@PathVariable("id") Long id) {
    return ResponseEntity.ok(couponService.getById(id));
  }

  @GetMapping("/code/{code}")
  public ResponseEntity<?> getByCode(@PathVariable("code") String code) {
    return ResponseEntity.ok(couponService.getByCode(code));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable("id") Long id, @Valid @RequestBody CouponRequest newCoupon) {
    return ResponseEntity.ok(couponService.update(id, newCoupon));
  }

  @DeleteMapping("/force-delete/{id}")
  public ResponseEntity<?> forceDelete(@PathVariable("id") Long id) {
    return ResponseEntity.ok(couponService.forceDelte(id));
  }
}
