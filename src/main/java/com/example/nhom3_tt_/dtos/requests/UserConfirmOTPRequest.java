package com.example.nhom3_tt_.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserConfirmOTPRequest {

  @NotNull(message = "Email is not null")
  private String email;

  @NotBlank(message = "OTP must not blank")
  private String OTP;
}
