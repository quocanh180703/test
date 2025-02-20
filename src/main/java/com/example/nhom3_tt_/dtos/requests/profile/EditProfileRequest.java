package com.example.nhom3_tt_.dtos.requests.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditProfileRequest {

  @NotBlank(message = "Full name is required")
  @Size(min = 6, max = 200, message = "Full name should be between 6 and 200 characters")
  private String fullname;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @Size(max = 15, message = "Phone number can have a maximum of 15 characters")
  @Pattern(regexp = "^[0-9]*$", message = "Phone number should contain only digits")
  private String phone;

  private String position;

  @Size(max = 500, message = "Description can have a maximum of 500 characters")
  private String description;
}
