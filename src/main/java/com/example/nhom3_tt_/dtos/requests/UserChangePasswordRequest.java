package com.example.nhom3_tt_.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordRequest {

  @NotBlank(message = "old password must not blank")
  private String old_password;

  @Pattern(
      regexp =
          "^(?!.*[\\p{So}])[\\w@.\\u3000-\\u303F\\u3040-\\u309F\\u30A0-\\u30FF\\uFF00-\\uFFEF\\u4E00-\\u9FAF\\u2605-\\u2606\\u2190-\\u2195\\u203B\\u00C0-\\u1EF9]*$",
      message = "Password cannot contain space, emoji or special characters other than '@' and '.'")
  @Size(min = 6, max = 50)
  private String new_password;

  @Size(min = 6, max = 50)
  private String confirm_password;
}
