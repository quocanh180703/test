package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.UserChangePasswordOTPRequest;
import com.example.nhom3_tt_.dtos.requests.UserChangePasswordRequest;
import com.example.nhom3_tt_.dtos.requests.UserConfirmOTPRequest;
import com.example.nhom3_tt_.dtos.requests.profile.EditProfileRequest;
import com.example.nhom3_tt_.dtos.response.profile.EditProfileResponse;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.mappers.ProfileMapper;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.UserRepository;
import com.example.nhom3_tt_.services.CloudinaryService;
import com.example.nhom3_tt_.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static com.example.nhom3_tt_.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  private final ProfileMapper profileMapper;

  private final CloudinaryService cloudinaryService;
  private final UserRepository userRepository;

  @Value("${default.thumbnail.url}")
  private String defaultAvatar;

  @Override
  public Optional<User> findByUsername(String username) {
    return repository.findByUsername(username);
  }

  @Override
  public User save(User user) {
    return repository.save(user);
  }

  @Override
  public String changePassword(UserChangePasswordRequest request) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    User user =
        repository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    boolean checkPassword = passwordEncoder.matches(request.getOld_password(), user.getPassword());

    if (!checkPassword) throw new AppException(WRONG_PASSWORD);

    if (!request.getNew_password().equals(request.getConfirm_password()))
      throw new AppException(PASSWORD_NOT_EQUAL);

    user.setPassword(passwordEncoder.encode(request.getNew_password()));
    repository.save(user);

    return "Change password Successful";
  }

  @Override
  public boolean confirmOtp(UserConfirmOTPRequest request) {

    checkOTP(request.getEmail(), request.getOTP());

    return true;
  }

  @Override
  public String changePasswordOTP(UserChangePasswordOTPRequest request) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    User user = checkOTP(request.getEmail(), request.getOtp());

    if (!request.getNewPassword().equals(request.getConfirmPassword()))
      throw new AppException(PASSWORD_NOT_EQUAL);

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    // delete otp
    user.setOTP(null);
    repository.save(user);

    return "Change password Successful";
  }

  private User checkOTP(String email, String otp) {
    User user = repository.findByEmail(email).orElseThrow(() -> new AppException(EMAIL_NOT_EXIST));

    if (!otp.equals(user.getOTP())) throw new AppException(OTP_NOT_SUCCESS);

    if (user.getOtpExpirationDate().before(new Date())) throw new AppException(OTP_IS_EXPIRY);

    return user;
  }

  @Override
  public EditProfileResponse getProfile(Long userId) {
    User user = getUserById(userId);
    return profileMapper.toProfileResponse(user);
  }

  @Override
  public EditProfileResponse editProfile(EditProfileRequest request, MultipartFile avatar)
      throws IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User userAuth = (User) authentication.getPrincipal();
    User user = getUserById(userAuth.getId());

    if (repository.existsByEmail(request.getEmail())) {
      throw new AppException(EMAIL_EXISTED);
    }

    String avatarUrl;
    if (avatar != null && !avatar.isEmpty()) {
      avatarUrl = cloudinaryService.uploadImage(avatar);
    } else {
      avatarUrl = defaultAvatar;
    }
    profileMapper.updateProfile(user, request);
    user.setAvatar(avatarUrl);
    User userResponse = repository.save(user);
    return profileMapper.toProfileResponse(userResponse);
  }

  @Override
  public User getUserById(Long id) {
    return repository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public String blockUnblockUser(Long userId) {
    User user = getUserById(userId);
    String newStatus;
    if (user.getStatus() == null || Boolean.TRUE.equals(user.getStatus())) {
      user.setStatus(false);
      newStatus = "User has been blocked";
    } else {
      user.setStatus(true);
      newStatus = "User has been activated";
    }
    repository.save(user);
    return newStatus;
  }
}
