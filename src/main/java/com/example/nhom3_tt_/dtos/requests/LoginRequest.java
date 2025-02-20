package com.example.nhom3_tt_.dtos.requests;

import com.example.nhom3_tt_.validator.NoSpace;
import com.example.nhom3_tt_.validator.NoSpecialCharacters;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.regex.qual.Regex;

import javax.annotation.RegEx;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

  @NoSpace(message = "Username cannot contain space")
  @NoSpecialCharacters(message = "Username cannot contain special characters")
  @Size(min = 6, max = 50, message = "Username is at least 6 character and highest is 50 character")
  private String username;

  @Size(min = 6, max = 30, message = "Password is at least 6 character and highest is 30 character")
  @Pattern(
      regexp =
          "^(?!.*[\\p{So}])[\\w@.\\u3000-\\u303F\\u3040-\\u309F\\u30A0-\\u30FF\\uFF00-\\uFFEF\\u4E00-\\u9FAF\\u2605-\\u2606\\u2190-\\u2195\\u203B\\u00C0-\\u1EF9]*$",
      message = "Password cannot contain space, emoji or special characters other than '@' and '.'")
  private String password;
}
