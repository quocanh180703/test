package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.dtos.requests.UserChangePasswordRequest;
import com.example.nhom3_tt_.dtos.requests.profile.EditProfileRequest;
import com.example.nhom3_tt_.dtos.response.profile.EditProfileResponse;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Context;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final UserService userService;

  // READ: Lấy thông tin hồ sơ người dùng
  @GetMapping("/get-profile")
  public ResponseEntity<EditProfileResponse> getProfile() {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    EditProfileResponse response = userService.getProfile(currentUser.getId());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/get-profile/{userId}")
  public ResponseEntity<EditProfileResponse> getProfile(@PathVariable Long userId) {
    EditProfileResponse response = userService.getProfile(userId);
    return ResponseEntity.ok().body(response);
  }

  // UPDATE: Cập nhật hồ sơ người dùng
  @PutMapping("/edit-profile")
  public ResponseEntity<EditProfileResponse> editProfile(
      @RequestPart("avatar") @Nullable MultipartFile avatar,
      @Valid @RequestPart("user") EditProfileRequest request)
      throws IOException {
    EditProfileResponse response = userService.editProfile(request, avatar);
    return ResponseEntity.ok().body(response);
  }
}
