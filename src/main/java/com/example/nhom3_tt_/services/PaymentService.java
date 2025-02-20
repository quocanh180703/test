package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.OrderCreationRequest;
import com.example.nhom3_tt_.dtos.PaymentVnpayResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PaymentService {
  PaymentVnpayResponse createVnPayPayment(
      HttpServletRequest request,
      int amount,
      String bankCode,
      String idStudent,
      OrderCreationRequest orderRequest)
      throws JsonProcessingException;
}
