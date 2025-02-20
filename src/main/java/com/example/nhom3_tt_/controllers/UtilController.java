package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.domain.RestResponse;
import com.example.nhom3_tt_.dtos.requests.UserChangePasswordOTPRequest;
import com.example.nhom3_tt_.dtos.requests.UserConfirmOTPRequest;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.UserRepository;
import com.example.nhom3_tt_.services.UserService;
import com.example.nhom3_tt_.util.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/util")
@RequiredArgsConstructor
public class UtilController {

  private final MailService mailService;

  private final UserService userService;

  private UserRepository userRepository;

  @PostMapping("/forgot-password")
  public ResponseEntity<?> sendEmail(
      @NotBlank(message = "Email is not blank") @RequestParam String email)
      throws MessagingException, UnsupportedEncodingException {

    User user =
        userService
            .findByEmail(email)
            .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXIST));

    CompletableFuture<String> result = mailService.sendEmail(user, email);

    result.exceptionally(ex -> "Error while sending email");

    return ResponseEntity.accepted().body(result);
  }

  @PostMapping("/confirm-otp")
  public ResponseEntity<?> confirmOTP(@Valid @RequestBody UserConfirmOTPRequest request)
      throws MessagingException, UnsupportedEncodingException {

    return ResponseEntity.ok().body(userService.confirmOtp(request));
  }

  @PutMapping("/change-password-otp")
  public ResponseEntity<?> changePasswordByOTP(
      @Valid @RequestBody UserChangePasswordOTPRequest request) {

    ObjectMapper objectMapper = new ObjectMapper();

    return ResponseEntity.ok()
        .body(
            objectMapper.createObjectNode().put("message", userService.changePasswordOTP(request)));
  }

  @PutMapping("/block-unblock/{userId}")
  public ResponseEntity<String> blockUnblockUser(@PathVariable Long userId) {
    String result = userService.blockUnblockUser(userId);
    return ResponseEntity.ok().body(result);
  }
}
