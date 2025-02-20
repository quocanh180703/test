package com.example.nhom3_tt_.dtos.requests;

import com.example.nhom3_tt_.validator.NoSpace;
import org.eclipse.angus.mail.handlers.message_rfc822;

import com.example.nhom3_tt_.validator.NoSpecialCharacters;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

  @Size(min = 6, max = 50, message = "Username is at least 6 character and highest is 50 character")
  @NoSpecialCharacters(
      message = "Username cannot contain special characters, emojis or space except _")
  @NoSpace(message = "Username cannot contain space")
  private String username;

  @Size(min = 6, max = 30, message = "Password is at least 6 character and highest is 30 character")
  @Pattern(
      regexp =
          "^(?!.*[\\p{So}])[\\w@.\\u3000-\\u303F\\u3040-\\u309F\\u30A0-\\u30FF\\uFF00-\\uFFEF\\u4E00-\\u9FAF\\u2605-\\u2606\\u2190-\\u2195\\u203B\\u00C0-\\u1EF9]*$",
      message =
          "Password cannot contain space, emojis or special characters other than '@' and '.'")
  private String password;

  @Email(message = "Email is invalid, the email address must follow the format: user123@gmail.com")
  private String email;

  @Size(
      min = 6,
      max = 200,
      message = "Full name is at least 6 character and highest is 200 character")
  @NoSpecialCharacters(message = "Full name cannot contain special characters or emojis except _")
  private String fullName;

  @NotNull(message = "Role is required")
  @Pattern(regexp = "^(STUDENT|INSTRUCTOR)$", message = "Role must be either STUDENT or INSTRUCTOR")
  private String role;
}
