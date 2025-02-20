package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.domain.RestResponse;
import com.example.nhom3_tt_.dtos.requests.UserChangePasswordRequest;
import com.example.nhom3_tt_.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PutMapping("/change-password")
  public ResponseEntity<?> changePassword(@Valid @RequestBody UserChangePasswordRequest request) {

    ObjectMapper mapper = new ObjectMapper();

    return ResponseEntity.ok()
        .body(mapper.createObjectNode().put("message", userService.changePassword(request)));
  }
}
