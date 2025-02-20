package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.config.payment.VNPAYConfig;
import com.example.nhom3_tt_.dtos.OrderCreationRequest;
import com.example.nhom3_tt_.dtos.PaymentVnpayResponse;
import com.example.nhom3_tt_.services.PaymentService;
import com.example.nhom3_tt_.util.VNPayUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
  private final VNPAYConfig vnPayConfig;

  @Override
  public PaymentVnpayResponse createVnPayPayment(
      HttpServletRequest request,
      int amount1,
      String bankCode,
      String idStudent,
      OrderCreationRequest orderRequest)
      throws JsonProcessingException {
    long amount = amount1 * 100L;
    Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(orderRequest, idStudent);

    ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
    ZonedDateTime now = ZonedDateTime.now(zoneId);
    ZonedDateTime expire = now.plusMinutes(15);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String createDate = now.format(formatter);
    String expireDate = expire.format(formatter);

    vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
    vnpParamsMap.put("vnp_CreateDate", createDate);
    vnpParamsMap.put("vnp_ExpireDate", expireDate);
    if (bankCode != null && !bankCode.isEmpty()) {
      vnpParamsMap.put("vnp_BankCode", bankCode);
    }
    vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
    // build query url
    String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
    String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
    String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
    queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
    String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    return PaymentVnpayResponse.builder()
        .code(00)
        .message("success")
        .paymentUrl(paymentUrl)
        .build();
  }
}
