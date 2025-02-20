package com.example.nhom3_tt_.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordOTPRequest {

  private String email;

  @Size(min = 6, max = 50)
  @NotNull(message = "Password must not null")
  private String newPassword;

  @Size(min = 6, max = 50)
  @NotNull(message = "Confirm password must not null")
  private String confirmPassword;

  @NotNull(message = "Otp must not null")
  private String otp;
}
