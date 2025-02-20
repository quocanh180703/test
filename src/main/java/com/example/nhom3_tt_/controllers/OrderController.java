package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  //  @PostMapping("/{studentId}")
  //  public ResponseEntity<?> createOrder(@PathVariable Long studentId) {
  //    return ResponseEntity.ok(orderService.createOrder(studentId));
  //  }

  @GetMapping("/{orderId}")
  public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
    return ResponseEntity.ok(orderService.getOrderById(orderId));
  }

  @GetMapping("/student/{studentId}")
  public ResponseEntity<?> getAllOrdersByStudentId(@PathVariable Long studentId) {
    return ResponseEntity.ok(orderService.getAllOrdersByStudentId(studentId));
  }

  //    @PostMapping("/details")
  //    public ResponseEntity<?> addOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest)
  // {
  //        return ResponseEntity.ok(orderService.addOrderDetail(orderDetailRequest));
  //    }
  @DeleteMapping("/{orderId}")
  public ResponseEntity<?> deleteOrderByOrderId(@PathVariable Long orderId) {
    orderService.deleteOrderByOrderId(orderId);
    return ResponseEntity.ok("Order deleted successfully for orderId: " + orderId);
  }

  @DeleteMapping("/orderDetails/{orderDetailId}")
  public ResponseEntity<?> deleteOrderDetailById(@PathVariable Long orderDetailId) {
    orderService.deleteOrderDetailById(orderDetailId);
    return ResponseEntity.ok(
        "OrderDetail deleted successfully for orderDetailId: " + orderDetailId);
  }
}
