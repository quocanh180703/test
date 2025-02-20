package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.OrderCreationRequest;
import com.example.nhom3_tt_.dtos.PaymentVnpayResponse;
import com.example.nhom3_tt_.dtos.response.OrderResponse;
import com.example.nhom3_tt_.dtos.response.invoiceDetail.Invoice;
import com.example.nhom3_tt_.dtos.response.invoiceDetail.InvoiceItem;
import com.example.nhom3_tt_.dtos.response.invoiceDetail.Subject;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.CustomException;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.UserRepository;
import com.example.nhom3_tt_.security.jwt.JwtService;
import com.example.nhom3_tt_.services.OrderDetailService;
import com.example.nhom3_tt_.services.OrderService;
import com.example.nhom3_tt_.services.PaymentService;
import com.example.nhom3_tt_.services.UserService;
import com.example.nhom3_tt_.util.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.example.nhom3_tt_.exception.ErrorCode.USER_NOT_FOUND;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/payment")
@Slf4j
public class PaymentVnpayController {
  private final PaymentService paymentService;
  private final OrderService orderService;
  private final UserRepository userRepository;
  private final UserService userService;
  private final OrderDetailService orderDetailService;
  private final MailService mailService;

  @PostMapping("/vn-pay")
  public ResponseEntity<PaymentVnpayResponse> pay(
      HttpServletRequest request,
      @RequestParam(required = false) List<String> couponCodes,
      @RequestBody OrderCreationRequest orderRequest)
      throws JsonProcessingException {
    // You can pass the addressId, paymentMethod, and products from the request body
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(USER_NOT_FOUND));

    boolean hasCoupon = couponCodes != null && !couponCodes.isEmpty();
    // kiểm tra trùng lặp trong couponCodes
    if (hasCoupon) {
      Set<String> uniqueCoupons = new HashSet<>(couponCodes);
      if (uniqueCoupons.size() != couponCodes.size()) {
        throw new CustomException(
            "Duplicate coupon codes are not allowed", HttpStatus.BAD_REQUEST.value());
      }
    }
    String amount =
        hasCoupon ? orderService.getAmount(user, couponCodes, orderRequest.getListProduct()) : orderService.getAmount(user, null,orderRequest.getListProduct());
    int intValue = (int) Double.parseDouble(amount);

    PaymentVnpayResponse response =
        paymentService.createVnPayPayment(
            request, intValue, "NCB", String.valueOf(user.getId()), orderRequest);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/vn-pay-callback")
  public ResponseEntity<?> payCallbackHandler(
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, MessagingException {
    // Extract VNPay callback parameters
    String status = request.getParameter("vnp_ResponseCode");
    String transactionId = request.getParameter("vnp_TransactionNo");

    String orderInfo = request.getParameter("vnp_OrderInfo");
    String[] infoParts = orderInfo.split("\\|");
    String orderId = infoParts[0];
    String listProduct = infoParts[1];
    String studentId = infoParts[2];

    Long vnp_Amount_temp = Long.valueOf(request.getParameter("vnp_Amount"));
    String vnp_Amount = String.valueOf(vnp_Amount_temp / 100);

    User user = userService.getUserById(Long.valueOf(studentId));

    if ("00".equals(status)){
      OrderResponse res = orderService.createOrder(user, vnp_Amount,listProduct);
      Invoice invoice = new Invoice();
      invoice.setId(transactionId);
      invoice.setTotal(vnp_Amount);
      invoice.setNotice(status.equals("00") ? "Success" : "Fail");
      invoice.setTo(Subject.builder().name(user.getFullname()).email(user.getEmail()).build());
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      invoice.setDate(formatter.format(res.getOrderDate()));
      List<InvoiceItem> items = new ArrayList<>();
      res.getOrderDetailIds().stream()
              .map(orderDetailService::getById)
              .forEach(
                      orderDetail -> {
                        InvoiceItem item = new InvoiceItem();
                        item.setDescription(orderDetail.getCourse().getTitle());
                        item.setTotalPrice(orderDetail.getPrice().toString());
                        items.add(item);
                      });
      invoice.setItems(items);

      mailService.sendInvoice(invoice);

      return ResponseEntity.ok(res);
    }else {
      // Log the error for monitoring purposes
      String errorMessage = String.format("VNPay payment failed: Status=%s, TransactionId=%s, OrderId=%s",
              status, transactionId, orderId);
      log.error(errorMessage);

      // Return a detailed error response
      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put("message", "Payment failed");
      errorResponse.put("transactionId", transactionId);
      errorResponse.put("status", status);
      errorResponse.put("orderId", orderId);

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
  }
}
