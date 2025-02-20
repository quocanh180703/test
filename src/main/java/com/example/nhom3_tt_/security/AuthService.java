package com.example.nhom3_tt_.security;

import com.example.nhom3_tt_.dtos.User.UserDTO;
import com.example.nhom3_tt_.dtos.requests.LoginRequest;
import com.example.nhom3_tt_.dtos.requests.RegisterRequest;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.models.ETypeRole;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.security.jwt.JwtService;
import com.example.nhom3_tt_.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.nhom3_tt_.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public UserDTO register(RegisterRequest req) {
    Optional<User> existedUser = userService.findByUsername(req.getUsername());

    if (existedUser.isPresent()) {
      throw new AppException(USER_NAME_EXISTED);
    }

    User user =
        User.builder()
            .username(req.getUsername())
            .password(passwordEncoder.encode(req.getPassword()))
            .fullname(req.getFullName())
            .email(req.getEmail())
            .role(ETypeRole.valueOf(req.getRole().toUpperCase()))
            .build();

    try {
      userService.save(user);
    } catch (DataIntegrityViolationException exception) {
      throw new AppException(ErrorCode.EMAIL_EXISTED);
    }

    return UserDTO.builder()
        .username(user.getUsername())
        .fullname(user.getFullname())
        .email(user.getEmail())
        .role(user.getRole().name())
        .build();
  }

  public Object login(LoginRequest req) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    } catch (AuthenticationException e) {
      throw new IllegalArgumentException("Invalid username or password");
    }
    Optional<User> user = userService.findByUsername(req.getUsername());
    if (user.isEmpty() || !passwordEncoder.matches(req.getPassword(), user.get().getPassword())) {
      throw new IllegalArgumentException("Invalid username or password");
    }
    var token = jwtService.generateAccessToken(user.get());

    return objectMapper.createObjectNode().put("access_token", token);
  }
}
