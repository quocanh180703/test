package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orderDetails")
@RequiredArgsConstructor
public class OrderDetailController {

  private final OrderDetailService orderDetailService;

  //  @PostMapping("")
  //  public ResponseEntity<?> create(@Valid @RequestBody OrderDetailRequest orderDetailRequest) {
  //    return ResponseEntity.ok(orderDetailService.create(orderDetailRequest));
  //  }

  @GetMapping("")
  public ResponseEntity<?> getAll(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(orderDetailService.getAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(orderDetailService.getById(id));
  }

  @GetMapping("/student/{id}")
  public ResponseEntity<?> getByStudentId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(orderDetailService.getByStudentId(id));
  }

  @GetMapping("/my-orderDetails")
  public ResponseEntity<?> myOrderDetails() {
    return ResponseEntity.ok(orderDetailService.myOrderDetails());
  }

  @GetMapping("/order/{id}")
  public ResponseEntity<?> getByOrderId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(orderDetailService.getByOrderId(id));
  }

  //  @PutMapping("/{id}")
  //  public ResponseEntity<?> update(
  //      @PathVariable("id") Long id, @Valid @RequestBody OrderDetailRequest newOrderDetail) {
  //    OrderDetailResponse response = orderDetailService.update(id, newOrderDetail);
  //    return ResponseEntity.ok(response);
  //  }

  @DeleteMapping("/force-delete/{id}")
  public ResponseEntity<?> forceDelete(@PathVariable("id") Long id) {
    return ResponseEntity.ok(orderDetailService.forceDelete(id));
  }
}
