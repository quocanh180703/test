package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.UserChangePasswordOTPRequest;
import com.example.nhom3_tt_.dtos.requests.UserChangePasswordRequest;
import com.example.nhom3_tt_.dtos.requests.UserConfirmOTPRequest;
import com.example.nhom3_tt_.dtos.requests.profile.EditProfileRequest;
import com.example.nhom3_tt_.dtos.response.profile.EditProfileResponse;
import com.example.nhom3_tt_.models.User;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

public interface UserService {
  Optional<User> findByUsername(String username);

  //  Optional<User> getUserByEmail(String email);

  User save(User user);

  String changePassword(UserChangePasswordRequest request);

  boolean confirmOtp(UserConfirmOTPRequest request);

  String changePasswordOTP(UserChangePasswordOTPRequest request);

  EditProfileResponse getProfile(Long userId);

  User getUserById(Long id);

  Optional<User> findByEmail(String email);

  EditProfileResponse editProfile(EditProfileRequest request, MultipartFile avatar)
      throws IOException;

  @PreAuthorize("hasAuthority('ADMIN')")
  String blockUnblockUser(Long userId);
}
